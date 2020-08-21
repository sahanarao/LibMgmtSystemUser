package com.libmgmtsys.usermgmt.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libmgmtsys.usermgmt.model.Role;
import com.libmgmtsys.usermgmt.model.User;
import com.libmgmtsys.usermgmt.services.UserService;



@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/service/registration")
    public ResponseEntity<?> register(@RequestBody List<User> user) {
    	User userData = new User();
    	for(User usr:user) {
    		userData.setFirstName(usr.getFirstName());
    		userData.setLastName(usr.getLastName());
    		userData.setUserName(usr.getUserName());
    		userData.setPassword(usr.getPassword());
    	}
        if (userService.findByUserName(userData.getUserName()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        userData.setRole(Role.USER);
        userService.saveUser(userData);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/service/users")
    public List<User> getUsers() {
        return userService.findUsers();
    }
    
    @PostMapping("/service/delete")
    public void delUsers(@RequestBody List<User> users) {
        userService.deleteAll(users);

    }
    
    @PostMapping("/login")
    public List<User> login(@RequestBody List<User> idList) {
    	List<User> list = new ArrayList<User>();
    	String userName = idList.iterator().next().getUserName();
    	String password = idList.iterator().next().getPassword();
    	User userdata = userService.findUser(userName,password);
    	list.add(userdata);
        return list;
    }
    
	@RequestMapping("/user")
	public Principal user(HttpServletRequest request) {
		String authToken = request.getHeader("Authorization").substring("Basic".length()).trim();
		return () -> new String(Base64.getDecoder().decode(authToken)).split(":")[0];
	}
}
