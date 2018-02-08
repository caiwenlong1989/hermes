package com.jlfex.hermes.main;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cfca.payment.api.tx.Tx1341Request;
import cfca.payment.api.tx.Tx134xResponse;

import com.alibaba.fastjson.JSON;
import com.jlfex.hermes.common.App;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.Result;
import com.jlfex.hermes.common.Result.Type;
import com.jlfex.hermes.common.cache.Caches;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.exception.ServiceException;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Numbers;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.common.web.RequestParam;
import com.jlfex.hermes.main.IndexController.HomeNav;
import com.jlfex.hermes.model.BankAccount;
import com.jlfex.hermes.model.BankAccount.Status;
import com.jlfex.hermes.model.Payment;
import com.jlfex.hermes.model.Transaction;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserAccount;
import com.jlfex.hermes.model.UserImage;
import com.jlfex.hermes.model.UserLog;
import com.jlfex.hermes.model.UserProperties;
import com.jlfex.hermes.model.Withdraw;
import com.jlfex.hermes.model.cfca.CFCAOrder;
import com.jlfex.hermes.service.AreaService;
import com.jlfex.hermes.service.AuthService;
import com.jlfex.hermes.service.BankAccountService;
import com.jlfex.hermes.service.BankService;
import com.jlfex.hermes.service.PaymentService;
import com.jlfex.hermes.service.TransactionService;
import com.jlfex.hermes.service.UserInfoService;
import com.jlfex.hermes.service.UserLogService;
import com.jlfex.hermes.service.UserService;
import com.jlfex.hermes.service.cfca.CFCAOrderService;
import com.jlfex.hermes.service.userAccount.UserAccountService;
import com.jlfex.hermes.service.web.PropertiesFilter;

/**
 * 账户中心控制器
 */
@Controller
@RequestMapping("/account")
public class AccountController {


	/** 银行账户业务接口 */
	@Autowired
	private BankAccountService bankAccountService;

	/** 用户业务接口 */
	@Autowired
	private UserService userService;

	/** 用户个人信息接口 */
	@Autowired
	private UserInfoService userInfoService;

	/** 交易业务接口 */
	@Autowired
	private TransactionService transactionService;

	/** 支付业务接口 */
	@Autowired
	private PaymentService paymentService;

	/** 地区业务接口 */
	@Autowired
	private AreaService areaService;
	@Autowired
	private BankService bankService;
	@Autowired
	private AuthService authService;
	@Autowired
	private CFCAOrderService cFCAOrderService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private UserLogService userLogService;
	
	/**
	 * 索引
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index(String type, Model model,String id,String loanId,String infoId,String bankAccountId) {
		App.checkUser();
		model.addAttribute("invests", id);
		model.addAttribute("loans", loanId);
		model.addAttribute("infos", infoId);
		model.addAttribute("nav", HomeNav.ACCOUNT);
		model.addAttribute("type", Strings.empty(type, null));
		model.addAttribute("bankAccountId", bankAccountId);
		return "account/index";
	}

	/**
	 * 认证中心
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/approve")
	public String approve(Model model) {
		// 查询用户数据
		App.checkUser();
		UserProperties userProperties = userService.loadPropertiesByUserId(App.user().getId());

		// 设置属性并渲染视图
		model.addAttribute("email", App.user().getAccount());
		model.addAttribute("userProp", userProperties);
		model.addAttribute("idTypes", Dicts.elements(UserProperties.IdType.class));
		model.addAttribute("phoneSwitch", Strings.equals(HermesConstants.SWITCH_FLAG_ZERO, App.config("auth.cellphone.switch").trim()));
		model.addAttribute("idSwitch", Strings.equals(HermesConstants.SWITCH_FLAG_ZERO, App.config("auth.realname.switch").trim()));
		return "account/approve-new";
	}

	/**
	 * 银行卡管理
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/bankCardManage")
	public String bankCardManage(Model model) {
		// 查询用户数据
		App.checkUser();
		BankAccount bankAccount = bankAccountService.findOneByUserIdAndStatus(App.user().getId(), Status.ENABLED);
		// 设置属性并渲染视图
		if (bankAccount != null) {
			model.addAttribute("area", areaService.loadById(bankAccount.getCity().getParentId()));
		}
		model.addAttribute("bankAccount", bankAccount);
		return "account/bankCardManage";
	}

	/**
	 * 新增银行卡页面
	 * 
	 * @return
	 */
	@RequestMapping("/addBankCard")
	public String addBankCard(Model model) {
		App.checkUser();
		model.addAttribute("userId", App.user().getId());
		model.addAttribute("banks", bankService.findAll());// 查询所有银行信息
		model.addAttribute("area", JSON.toJSONString(areaService.getAllChildren(null)));
		model.addAttribute("realName", userService.loadPropertiesByUserId(App.user().getId()).getRealName());// 获取持卡人的真实姓名
		model.addAttribute("userProperties", userService.loadPropertiesByUserId(App.user().getId()));
		return "account/addBankCard";
	}

	/**
	 * 编辑银行卡页面
	 * 
	 * @return
	 */
	@RequestMapping("/editBankCard/{id}")
	public String editBankCard(@PathVariable("id") String id, Model model) {
		model.addAttribute("bankAccount", bankAccountService.loadBankAccountById(id));
		model.addAttribute("banks", bankService.findAll());// 查询所有银行信息
		model.addAttribute("area", JSON.toJSONString(areaService.getAllChildren(null)));
		// model.addAttribute("areaRoots", areaService.findByParentIsNull());//
		// 查询area根级数据
		// model.addAttribute("areaChildrens",
		// areaService.loadByParentId(bankAccountService.loadBankAccountById(id).getCity().getParent().getId()));//
		// 查询area子数据
		return "account/editBankCard";
	}

	/**
	 * 处理更换银行卡业务
	 * 
	 * @return
	 */
	@RequestMapping("handerEditBankCard/{id}")
	@ResponseBody
	public Result<String> handerEditBankCard(@PathVariable("id") String id, HttpServletRequest request) {
		String bankId = request.getParameter("bankId");
		String cityId = request.getParameter("cityId");
		String deposit = request.getParameter("deposit");
		String account = request.getParameter("account");
		String isdefault = request.getParameter("isdefault");
		return authService.editBankCard(id, bankId, cityId, deposit, account, isdefault);
	}

	/**
	 * 资金明细
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail")
	public String detail(Model model) {
		// 加载数据
		App.checkUser();
		User user = userService.loadById(App.user().getId());
		UserProperties prop = userService.loadPropertiesByUserId(App.user().getId());
		UserAccount cash = userInfoService.loadAccountByUserAndType(user, UserAccount.Type.CASH);
		UserAccount frozen = userInfoService.loadAccountByUserAndType(user, UserAccount.Type.FREEZE);
		String avatar = userService.getAvatar(user, UserImage.Type.AVATAR_LG);

		// 设置参数并渲染视图
		model.addAttribute("beginDate", Calendars.date(Calendars.add(new Date(), Calendar.DATE, -7)));
		model.addAttribute("endDate", Calendars.date());
		model.addAttribute("user", user);
		model.addAttribute("prop", prop);
		model.addAttribute("pass", UserProperties.Auth.PASS);
		model.addAttribute("avatar", avatar);
		model.addAttribute("cash", cash);
		model.addAttribute("frozen", frozen);
		return "account/detail";
	}

	/**
	 * 资金明细数据
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param page
	 * @param size
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail/table")
	public String detailTable(String beginDate, String endDate, Integer page, Integer size, Model model) {
		App.checkUser();
		model.addAttribute("transaction", transactionService.findByUserIdAndDateBetweenAndTypes(App.user().getId(), beginDate, endDate, page, size));
		model.addAttribute("freeze", Transaction.Type.FREEZE);
		model.addAttribute("unfreeze", Transaction.Type.REVERSE_UNFREEZE);
		return "account/detail-table";
	}

	/**
	 * 账户充值
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/charge")
	public String charge(Model model) {
		App.checkUser();
		model.addAttribute("account", userInfoService.loadByUserIdAndType(App.user().getId(), UserAccount.Type.CASH));
		return "account/charge";
	}

	/**
	 * 计算充值手续费
	 * 
	 * @param amount
	 * @return
	 */
	@RequestMapping("/charge/calc")
	@ResponseBody
	public Result<String> calcChargeFee(Double amount) {
		// 计算手续费
		BigDecimal fee = paymentService.calcChargeFee(amount);
		BigDecimal sum = fee.add(Numbers.currency(amount));

		// 设置结果
		Result<String> result = new Result<String>(Result.Type.SUCCESS);
		result.addMessage(Numbers.toCurrency(fee));
		result.addMessage(Numbers.toCurrency(sum));

		// 返回结果
		return result;
	}

	/**
	 * 添加充值
	 * 
	 * @param channel
	 * @param amount
	 * @return
	 */
	@RequestMapping("/charge/add")
	public String addCharge(String channel, Double amount, Model model) {
		App.checkUser();
		Payment payment = paymentService.save(channel, amount);
		// 模拟充值
		transactionService.fromCropAccount(Transaction.Type.CHARGE, payment.getUser(), UserAccount.Type.PAYMENT, payment.getAmount(), payment.getId(), App.message("transaction.charge"));
		// 扣除充值手续
		transactionService.betweenCropAccount(Transaction.Type.OUT, UserAccount.Type.PAYMENT, UserAccount.Type.PAYMENT_FEE, payment.getFee(), payment.getId(), App.message("transaction.charge.fee"));
		model.addAttribute("payment", payment);
		return "account/charge-success";
	}

	/**
	 * 支付渠道选择
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/channels")
	public String channels(Model model) {
		model.addAttribute("channels", paymentService.findChannelByGroupAll().entrySet());
		return "account/channels";
	}

	/**
	 * 支付返回
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/payment/return/{id}")
	public String returnPayment(@PathVariable("id") String id, HttpServletRequest request, Model model) {
		model.addAttribute("result", paymentService.callback(id, new RequestParam(request), PaymentService.TYPE_RETURN));
		return "account/payment-return";
	}

	/**
	 * 支付通知
	 * 
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/payment/notify/{id}")
	public void notifyPayment(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
		// 处理并获得结果
		Result<Payment> result = paymentService.callback(id, new RequestParam(request), PaymentService.TYPE_NOTIFY);

		// 页面输出
		try {
			response.getWriter().flush();
			response.getWriter().write(result.getFirstMessage());
		} catch (IOException e) {
			throw new ServiceException("cannot write result");
		} finally {
			try {
				response.getWriter().close();
			} catch (IOException e) {
				Logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 提现
	 * 
	 * @return
	 */
	@RequestMapping("/withdraw")
	public String withdraw(Model model) {
		App.checkUser();
		model.addAttribute("bankAccounts", bankAccountService.findEnbaled());
		model.addAttribute("account", userInfoService.loadByUserIdAndType(App.user().getId(), UserAccount.Type.CASH));
		model.addAttribute("feeType", App.config(HermesConstants.WITHDRAW_FEE_SWITCH).trim());
		model.addAttribute("feeMaxAmount", "单笔最高金额："+App.config(HermesConstants.WITHDRAW_FEE_MAX_AMOUNT)); 
		return "account/withdraw";
	}

	/**
	 * 计算提现手续费
	 * 
	 * @param amount
	 * @return
	 */
	@RequestMapping("/withdraw/calc")
	@ResponseBody
	public Result<String> calcWithdrawFee(Double amount) {
		return bankAccountService.calcWithdrawFee(amount);
	}

	/**
	 * 保存提现
	 * 
	 * @param bankAccountId
	 * @param amount
	 * @return
	 */
	@RequestMapping("/withdraw/add")
	public String addWithdraw(String bankAccountId, BigDecimal amount, Model model) {
		// 初始化
		App.checkUser();
		Result<String> result = new Result<String>();
		// 保存数据
		try {
			User investUser = userService.loadById( App.user().getId());
			userLogService.saveUserLog(investUser, UserLog.LogType.WITHDRAW);
			if(amount == null || amount.compareTo(BigDecimal.ZERO) < 1){
				throw new Exception("提现金额为空");
			}
			amount = amount.setScale(2, RoundingMode.HALF_EVEN);
			BigDecimal  sumAmount = BigDecimal.ZERO;
			Result<String> calcResult =  calcWithdrawFee(amount.doubleValue());
			if(Result.Type.SUCCESS.equals(calcResult.getType())){
				List<String> amountList = calcResult.getMessages();
				sumAmount = new BigDecimal(amountList.get(1).replace(",", ""));
			}
			
			boolean enoughFlag = userAccountService.checkEnoughCash(investUser, UserAccount.Type.CASH, sumAmount);
			if(!enoughFlag && sumAmount.compareTo(amount) >= 0 ){
			   throw new Exception("当前现金余额不足");
			}
			//接入中金提现结算
			BigDecimal zjClearAmount = amount.multiply(new BigDecimal("100")); //分为单位
			Tx1341Request request = cFCAOrderService.buildTx1341Request(investUser,zjClearAmount);
			Tx134xResponse tx1341Response =  cFCAOrderService.invokeTx1341(request);
			if(HermesConstants.CFCA_SUCCESS_CODE.equals(tx1341Response.getCode())){
				//资金冻结
				Withdraw withDraw = bankAccountService.addWithdraw(bankAccountId, amount, request.getSerialNumber());
				//保存订单
				cFCAOrderService.genClearOrder(tx1341Response,investUser,amount, withDraw.getFee(), request.getSerialNumber(),CFCAOrder.Type.CLEAR);
			}else{
				throw new  Exception("中金结算接口异常：接口响应码="+tx1341Response.getCode());
			}
			result.setType(Result.Type.SUCCESS);
			result.addMessage("您的提现申请正在处理中，预计1-3个工作日到账。</br>如有疑问，请联系客服。");
		} catch (Exception e) {
			result.setType(Result.Type.FAILURE);
			result.addMessage(e.getMessage()==null?"提现失败,请重试":e.getMessage());
			Logger.error("提现异常", e);
		}
		// 渲染视图
		model.addAttribute("result", result);
		return "result-" + result.getTypeName();
	}

	/**
	 * 支付
	 * 
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/payment/{id}")
	public String payment(@PathVariable("id") String id, HttpServletRequest request, Model model) {
		model.addAttribute("form", paymentService.getPaymentForm(id, request.getRemoteAddr()));
		return "account/payment";
	}

	/**
	 * 银行账户表单
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/bank-account/form")
	public String bankAccountForm(Model model) {
		App.checkUser();
		model.addAttribute("banks", bankAccountService.findEnabledBank());
		model.addAttribute("properties", userInfoService.loadPropertiesByUserId(App.user().getId()));
		model.addAttribute("area", JSON.toJSONString(areaService.getAllChildren(null)));
		return "account/bank-account-form";
	}

	/**
	 * 保存银行账单
	 * 
	 * @param bankId
	 * @param cityId
	 * @param deposit
	 * @param account
	 * @return
	 */
	@RequestMapping("/bank-account/save")
	@ResponseBody
	public BankAccount saveBankAccount(String bankId, String cityId, String deposit, String account) {
		App.checkUser();
		return bankAccountService.save(bankId, cityId, deposit, account);
	}

	/**
	 * 清除缓存
	 * 
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/clear")
	public void clear(HttpServletResponse response) throws IOException {
		Caches.clear();
		PropertiesFilter.clear();
		response.getWriter().write("success");
	}

	/**
	 * 中金代扣充值
	 * 
	 * @param channel
	 * @param amount
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/charge/zjCharge")
	@ResponseBody
	public Result<String> zjCharge(BigDecimal amount) {
		BigDecimal fee = paymentService.calcChargeFee(amount.doubleValue());
		return  bankAccountService.zjCharge(amount,fee);
	}
	/**
	 * 充值结果
	 * @param message
	 * @param type
	 * @param pAmount
	 * @param model
	 * @return
	 */
	@RequestMapping("/charge/chargeResult")
	public String chargeResult(String message, String type,BigDecimal pAmount,String naviFlag, Model model) {
		App.checkUser();
		model.addAttribute("message", message);
		model.addAttribute("amount", pAmount);
		model.addAttribute("naviFlag", naviFlag);
		if (type.equals(Type.SUCCESS.name())) {
			model.addAttribute("type", "ico8.png");
		} else if (type.equals(Type.FAILURE.name())) {
			model.addAttribute("type", "ico9.png");
		} else {
			model.addAttribute("type", "ico7.png");
		}
		return "account/chargeResult";
	}
	
}
