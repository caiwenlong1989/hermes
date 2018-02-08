package com.jlfex.hermes.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.jlfex.hermes.common.dict.Dicts;
import com.jlfex.hermes.common.dict.Element;
import com.jlfex.hermes.common.utils.Calendars;
import com.jlfex.hermes.common.utils.Numbers;

/**
 * 提现信息模型
 */
@Entity
@Table(name = "hm_withdraw")
public class Withdraw extends Model {

	private static final long serialVersionUID = 422761529337836705L;

	/** 用户 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	/** 银行帐号 */
	@ManyToOne
	@JoinColumn(name = "bank_account")
	private BankAccount bankAccount;

	/** 日期 */
	@Column(name = "datetime")
	private Date datetime;

	/** 金额 */
	@Column(name = "amount")
	private BigDecimal amount;

	/** 手续费 */
	@Column(name = "fee")
	private BigDecimal fee;

	/** 备注 */
	@Column(name = "remark")
	private String remark;

	/** 状态 */
	@Column(name = "status")
	private String status;
	/** 提现流水**/
	@Column(name="serial_no")
	private String serialNo;
	

	/**
	 * 读取用户
	 * 
	 * @return
	 * @see #user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * 设置用户
	 * 
	 * @param user
	 * @see #user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 读取银行帐号
	 * 
	 * @return
	 * @see #bankAccount
	 */
	public BankAccount getBankAccount() {
		return bankAccount;
	}

	/**
	 * 设置银行帐号
	 * 
	 * @param bankAccount
	 * @see #bankAccount
	 */
	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * 读取日期
	 * 
	 * @return
	 * @see #datetime
	 */
	public Date getDatetime() {
		return datetime;
	}

	/**
	 * 设置日期
	 * 
	 * @param datetime
	 * @see #datetime
	 */
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	/**
	 * 读取金额
	 * 
	 * @return
	 * @see #amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 设置金额
	 * 
	 * @param amount
	 * @see #amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 读取手续费
	 * 
	 * @return
	 * @see #fee
	 */
	public BigDecimal getFee() {
		return fee;
	}

	/**
	 * 设置手续费
	 * 
	 * @param fee
	 * @see #fee
	 */
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	/**
	 * 读取备注
	 * 
	 * @return
	 * @see #remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置备注
	 * 
	 * @param remark
	 * @see #remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 读取状态
	 * 
	 * @return
	 * @see #status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置状态
	 * 
	 * @param status
	 * @see #status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 读取格式化时间
	 * 
	 * @return
	 */
	public String getFormatDatetime() {
		if (datetime != null)
			return Calendars.dateTime(datetime);
		return "";
	}

	/**
	 * 读取格式化金额
	 * 
	 * @return
	 */
	public String getFormatAmount() {
		if (amount != null)
			return Numbers.toCurrency(amount);
		return "";
	}

	/**
	 * 读取格式化手续费
	 * 
	 * @return
	 */
	public String getFormatFee() {
		if (fee != null)
			return Numbers.toCurrency(fee);
		return "";
	}

	/**
	 * 读取状态名称
	 * 
	 * @return
	 */
	public String getStatusName() {
		return Dicts.name(status, status, Status.class);
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}



	/**
	 * 状态
	 */
	public static final class Status {

		@Element("等待提现")
		public static final String WAIT = "00";

		@Element("成功")
		public static final String SUCCESS = "10";

		@Element("失败")
		public static final String FAILURE = "20";
	}
}
