package com.libmgmtsys.usermgmt.services;

import java.util.List;

import com.libmgmtsys.usermgmt.model.User;

public interface UserService {
    void saveUser(User user);

    String findByUserName(String userName);

    List<User> findUsers();
    
    User findUser(String userName, String password);
    
    void deleteAll(List<User> user);

}
