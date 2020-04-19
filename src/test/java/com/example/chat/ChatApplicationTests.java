package com.example.chat;

import com.example.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class ChatApplicationTests {

    @Test
    void contextLoads() {
        User user = new User();
        user.setAge(11);
        user.setMobile("asd");
        System.out.println(user.getAge()+"!!!!!!!!!!!!!");
        List<User> userList = new ArrayList<User>();
        userList = userList.stream().filter(data->data.getMobile().equals("13902348307")).collect(Collectors.toList());

    }

}
