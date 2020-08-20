package com.libmgmtsys.usermgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.libmgmtsys.usermgmt.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u.userName from User u WHERE u.userName in (:userName)") 
    String findByUserName(String userName);

	/*
	 * @Query("SELECT u.name from User u WHERE u.id in (:pIdList)") List<String>
	 * findUserNames(@Param("pIdList") List<Long> idList);
	 */
    
	 @Query("SELECT u.id,u.role,u.password from User u WHERE u.userName in (:userName)") 
	 List<Object[]> findUser(@Param("userName") String userName);
	 
}
