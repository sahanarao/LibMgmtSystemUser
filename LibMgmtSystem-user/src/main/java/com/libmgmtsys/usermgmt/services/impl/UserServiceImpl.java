package com.libmgmtsys.usermgmt.services.impl;

import java.util.Iterator;
import java.util.List;

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

    @Autowired
    UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<String> findUsers(List<Long> idList) {
       // return userRepository.findUserNames(idList);
   return null;
    }

	@Override
	public User findUser(String userName, String password) {
		// TODO Auto-generated method stub
		User user = new User();
		List<Object[]> res = userRepository.findUser(userName, password);
		Iterator<Object[]> it = res.iterator();
		while(it.hasNext()){
			Object[] line = it.next();
			
			user.setId((Long) line[0]);
			user.setRole((Role) line[1]);
		}
		return user;
	}
}
