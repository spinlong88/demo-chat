package com.example.chat;

import com.example.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatApplicationTests {

    @Test
    void contextLoads() {
        User user = new User();
        user.setAge(11);
        user.setMobile("asd");
        System.out.println(user.getAge()+"!!!!!!!!!!!!!");
    }

}
