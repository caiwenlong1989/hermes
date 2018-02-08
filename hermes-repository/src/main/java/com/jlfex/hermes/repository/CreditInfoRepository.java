package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.CrediteInfo;
import com.jlfex.hermes.model.Creditor;

/**
 * 债权信息 仓库
 * @author Administrator
 *
 */
@Repository
public interface CreditInfoRepository extends JpaRepository<CrediteInfo, String>, JpaSpecificationExecutor<CrediteInfo> {

	/**
	 * 通过状态查询债权信息
	 * 
	 * @param status
	 * @return
	 */
	public List<CrediteInfo> findByStatusIn(List<String> status);
	
	/**
	 * 根据：债权人 + 债权编号 获取债权信息
	 * @param creditor
	 * @param crediteCode
	 * @return
	 */
	public CrediteInfo  findByCreditorAndCrediteCode(Creditor creditor ,String crediteCode);
	/**
	 * 根据：债权人编号 + 债权编号 获取债权信息
	 * @return
	 */
	@Query("select t from CrediteInfo t where  t.creditor.creditorNo = ?1 and t.crediteCode = ?2")
	public List<CrediteInfo>  findByCreditorNoAndCrediteCode(String creditorNo , String crediteCode);
	
	/**
	 * 根据：债权编号 获取债权信息
	 * @param creditor
	 * @param crediteCode
	 * @return
	 */
	public CrediteInfo  findByCrediteCode(String crediteCode);
	
	/**
	 * 根据：债权人  获取债权信息
	 * @param creditor
	 * @param crediteCode
	 * @return
	 */
	public  List<CrediteInfo>  findByCreditor(Creditor creditor);
}
