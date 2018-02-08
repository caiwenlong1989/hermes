package com.jlfex.hermes.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.CreditRepayPlan;
import com.jlfex.hermes.model.CrediteInfo;

/**
 * 债权 还款明细  仓库
 * @author Administrator
 *
 */
@Repository
public interface CreditorRepayPlanRepository extends JpaRepository<CreditRepayPlan, String> ,JpaSpecificationExecutor<CreditRepayPlan>{

	/**
	 * 查询当前债权的还款明细 
	 * @param crediteInfo
	 * @return
	 */
	public List<CreditRepayPlan>  findByCrediteInfo(CrediteInfo crediteInfo) ;
	
	/**
	 * 根据：债权信息
	 * 获取：还款计划时间 < 截止当前时间 的还款明细 
	 * @return
	 */
	@Query("select t from CreditRepayPlan t where  t.repayPlanTime < ?1 and crediteInfo = ?2")
	public List<CreditRepayPlan>  findByNowTimeAndCreditInfo(Date endToday,CrediteInfo crediteInfo);
	/**
	 * 根据： 债权ID获取 明细
	 * @param crediteInfo
	 * @return
	 */
	@Query("select t from CreditRepayPlan t where   crediteInfo = ?1 order by t.period asc ")
	public List<CreditRepayPlan>  findByCreditInfoAscPeriod(CrediteInfo crediteInfo);
	
	@Query("from CreditRepayPlan t where   crediteInfo = ?1")
	public List<CreditRepayPlan> findByCreditInfo(CrediteInfo crediteInfo) ;

	/**
	 * 根据状态 查询当前债权明细 
	 * @param crediteInfo
	 * @return
	 */
	public List<CreditRepayPlan> findByCrediteInfoAndStatus(CrediteInfo crediteInfo,String status) ;
	
}
