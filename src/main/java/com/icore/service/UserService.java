package com.icore.service;

import com.icore.model.UserModel;
import com.icore.repository.person.UserModelMapper;
import com.icore.util.FastJsonUtil;
import com.icore.util.MicroLogFactory;
import com.icore.util.MicroLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserModelMapper userModelMapper;

    private static final MicroLogUtil log = MicroLogFactory.getLooger();

    public List<UserModel> getUserList(){
        log.info(" UserService  getUserList  success !");
        return userModelMapper.getUserList();
    }

    public UserModel getUser(){
        log.info(" UserService  getUser  success !");
        return userModelMapper.getUser();
    }

    public void addUser(UserModel userModel){
        userModelMapper.addUser(userModel);
        log.info(" UserService  addUser  success in user={}!", FastJsonUtil.convertObjectToJSON(userModel));
    }

    public void dealeteUser(Long id){
        try {
            userModelMapper.deleteUser(id);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        log.info(" UserService  deleteUser  success in id={}!", id);
    }


}
