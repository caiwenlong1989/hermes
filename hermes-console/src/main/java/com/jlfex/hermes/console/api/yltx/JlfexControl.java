package com.jlfex.hermes.console.api.yltx;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;
import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.model.yltx.FinanceOrder;
import com.jlfex.hermes.service.api.yltx.JlfexService;
import com.jlfex.hermes.service.pojo.yltx.response.QueryFinanceRspVo;

/**
 * 易联天下对接
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/jlfex")
public class JlfexControl {
	
	@Autowired
	private  JlfexService  jlfexService;

	@RequestMapping("/queryfinance")
	public String queryFinanceOrder(Model model) throws Exception {
		//接口同步
		int financeOrderSize = 0;  //理财产品总数
		int dealSucSize = 0;       //处理成功数
		int dealFailSize = 0;      //处理失败数
		int undoSize= 0;           //重复订单，不需处理数
		int invalidSize = 0;           //无效的理财产品
		String createDate = Calendars.format(HermesConstants.FORMAT_10);
		QueryFinanceRspVo queryFinanceRspVo = null;
		try{
			String jsonOrder = jlfexService.queryFinanceOrder(null, createDate, HermesConstants.JL_PAGE_SIZE, HermesConstants.JL_PAGE_NUM);
			queryFinanceRspVo = JSON.parseObject(jsonOrder,QueryFinanceRspVo.class);
			if(queryFinanceRspVo == null){
				model.addAttribute("synchResult", "易联接口异常，请查看接口日志");
				return "credit/importLoan";
			}
		}catch(Exception e){
			Logger.error("理财产品查询接口异常：", e);
			model.addAttribute("synchResult", "易联接口异常，请查看接口日志");
			return "credit/importLoan";
		}
		//数据处理
		List<FinanceOrder> financeOrderList = new  ArrayList<FinanceOrder>();
		jlfexService.buildFinanceOrder(financeOrderList, queryFinanceRspVo);
		financeOrderSize = financeOrderList.size();
		for(FinanceOrder obj: financeOrderList){
			try{
				String  resultVal = null;
				if(!( HermesConstants.FINANCE_FUNDRAISING.equals(obj.getStatus().trim()) ||
				      HermesConstants.FINANCE_WAIT_PAY.equals(obj.getStatus().trim()))	){
					 Logger.info("理财产品id="+obj.getUniqId()+", 状态为="+obj.getStatus()+",不进行处理");
					continue ;
				}
				int assetSize = obj.getAssetsList().size();
				if(assetSize>1){
					invalidSize ++ ;
					Logger.info("业务规则：只同步含一个资产的理财产品，理财产品id="+obj.getUniqId()+",含有子资产数="+assetSize);
					continue ;
				}
				String  resultCode = jlfexService.sellCreditDeal(obj);
				if("00".equals(resultCode)){
					resultVal = "发售失败";
					dealFailSize ++ ;
				}else if("01".equals(resultCode)){
					resultVal = "发售成功";
					dealSucSize ++ ;
				}else if("02".equals(resultCode)){
					resultVal = "不需重复发售";
					undoSize ++ ;
				}else{
					dealFailSize ++ ;
					resultVal = "发售失败";
				}
				Logger.info("理财产品id="+obj.getUniqId()+"处理结果： "+resultVal);
			}catch(Exception e){
				dealFailSize ++ ;
				Logger.error("理财产品id="+obj.getUniqId()+"处理异常：",e);
				continue;
			}
		}
		model.addAttribute("synchResult", "本次同步理财产品总数:"+financeOrderSize+"，发售成功数："+dealSucSize+",发售失败数:"+dealFailSize+"，不需重复发售数:"+undoSize+",不符合业务规则数："+invalidSize);
		return "credit/importLoan";
	} 	
}
