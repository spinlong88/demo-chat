package com.icore.service;

import com.icore.repository.UserMapper;
import com.icore.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;


    public List<UserModel> getUser(){
        return userMapper.getUser();
    }

    public void addUser(UserModel userModel){
        userMapper.addUser(userModel);
    }


}
