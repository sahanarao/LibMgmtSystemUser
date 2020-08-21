package com.libmgmtsys.usermgmt.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.libmgmtsys.usermgmt.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u.userName from User u WHERE u.userName in (:userName)") 
    String findByUserName(String userName);

	
	  @Query("SELECT u.id,u.firstName,u.lastName,u.userName from User u") 
	  List<Object[]> findUsers();
	 
    
	 @Query("SELECT u.id,u.role,u.password from User u WHERE u.userName in (:userName)") 
	 List<Object[]> findUser(@Param("userName") String userName);
	 
	 @Transactional
	 @Modifying
	 @Query(value = "delete from Borrow c where c.user_id = ?1") 
	 void delBorrowDetails(@Param("user_id") String user_id);
	 
}
