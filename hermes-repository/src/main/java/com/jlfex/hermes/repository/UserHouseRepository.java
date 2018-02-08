package com.jlfex.hermes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.model.UserHouse;

/**
 * 用户房产信息仓库
 */
@Repository
public interface UserHouseRepository extends JpaRepository<UserHouse, String>, JpaSpecificationExecutor<UserHouse> {

	/**
	 * 根据用户查找房产信息
	 * 
	 * @param user
	 * @return
	 */
	List<UserHouse> findByUser(User user);

	/**
	 * 根据用户id查找房产信息
	 * 
	 * @param userId
	 * @return
	 */
	List<UserHouse> findByUserId(String userId);
}
