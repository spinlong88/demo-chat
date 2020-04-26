package com.icore.repository;

import com.icore.model.UserModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    //获取用户名单
    @Select("select * from user ")
    public List<UserModel> getUser() ;

    //根据id删除用户
    @Delete(" delete from user where id =#{id}")
    public void deleteUser(int id)throws Exception;

    //新增用户
    @Options(useGeneratedKeys=true,keyProperty="id")
    @Insert("insert into user(username,age) values(#{username},#{age})")
    public void addUser(UserModel userModel);

}
