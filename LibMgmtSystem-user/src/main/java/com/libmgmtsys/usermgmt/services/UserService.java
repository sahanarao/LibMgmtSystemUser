package com.libmgmtsys.usermgmt.services;

import java.util.List;

import com.libmgmtsys.usermgmt.model.User;

public interface UserService {
    void saveUser(User user);

    String findByUserName(String userName);

    List<String> findUsers(List<Long> idList);
    
    User findUser(String userName, String password);

}
