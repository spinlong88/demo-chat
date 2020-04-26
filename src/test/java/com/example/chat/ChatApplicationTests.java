package com.example.chat;

import com.icore.model.UserModel;
import com.icore.model.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class ChatApplicationTests {

    @Test
    void contextLoads() {
        UserModel user = new UserModel();
        user.setAge(11);
        user.setMobile("asd");
        System.out.println(user.getAge()+"!!!!!!!!!!!!!");
        List<UserModel> userList = new ArrayList<UserModel>();
        userList = userList.stream().filter(data->data.getMobile().equals("13902348307")).collect(Collectors.toList());

    }

}
