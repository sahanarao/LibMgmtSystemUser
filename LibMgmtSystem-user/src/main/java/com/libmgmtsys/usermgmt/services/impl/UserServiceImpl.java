package com.libmgmtsys.usermgmt.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.libmgmtsys.usermgmt.model.Role;
import com.libmgmtsys.usermgmt.model.User;
import com.libmgmtsys.usermgmt.repository.UserRepository;
import com.libmgmtsys.usermgmt.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public String findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<User> findUsers() {
    	logger.info("FindUsers invoked");
    	List<User> usrList = new ArrayList<User>();
    	List<Object[]> res =  userRepository.findUsers();
    	Iterator<Object[]> it = res.iterator();
		while(it.hasNext()){
			User user = new User();
			Object[] line = it.next();			
			user.setId((Long) line[0]);
			user.setFirstName((String) line[1]);
			user.setLastName((String) line[2]);
			user.setUserName((String) line[3]);
			usrList.add(user);			
		}
		return usrList;
    }

	@Override
	public User findUser(String userName, String password) {
		// TODO Auto-generated method stub
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User user = new User();
		List<Object[]> res = userRepository.findUser(userName);
		Iterator<Object[]> it = res.iterator();
		while(it.hasNext()){
			Object[] line = it.next();			
			user.setId((Long) line[0]);
			user.setRole((Role) line[1]);
			user.setPassword((String) line[2]);
		}
		if(passwordEncoder.matches(password, user.getPassword())){
			return user;
		}
		return null;
	}

	@Override
	public void deleteAll(List<User> usr) {
		// TODO Auto-generated method stub
		logger.info("Delete User invoked");
		userRepository.deleteAll(usr);
		
	}
}
