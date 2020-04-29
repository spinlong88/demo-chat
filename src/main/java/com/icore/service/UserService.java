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
        return userModelMapper.getUserList();
    }

    public UserModel getUser(Long id){
        return userModelMapper.getUser(id);
    }

    public void addUser(UserModel userModel){
        userModelMapper.addUser(userModel);
    }

    public void updateUser(UserModel userModel){
        userModelMapper.updateUser(userModel);
    }

    public void dealeteUser(Long id){
        try {
            userModelMapper.deleteUser(id);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


}
