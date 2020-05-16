package com.icore.repository.person;

import com.icore.model.UserModel;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserModelMapper {

    //获取用户名单
    public List<UserModel> getUserList() ;

    //获取用户名单
    public UserModel getUser(Long id) ;

    //根据id删除用户
    public void delUser(long id)throws Exception;

    //新增用户
    public void addUser(UserModel userModel);

    //修改用户
    public void updateUser(UserModel userModel);

}
