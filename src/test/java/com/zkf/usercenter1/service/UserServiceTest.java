package com.zkf.usercenter1.service;

import com.zkf.usercenter1.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户服务测试
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setId(0L);
        user.setUsername("deat4");
        user.setUserAccount("123");
        user.setAvatarUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzendCLFDjig_ogFcWDscaNMaZIjk3aYisUmtM-mBYlZImluCC");
        user.setGender(0);
        user.setUserPassword("admin123");
        user.setPhone("123");
        user.setEmail("456");
        user.setUserStatus(0);
        user.setIsDelete(0);
        boolean result = userService.save(user);
        System.out.println(user.getId());
        assertTrue(result);
    }

//    @Test
//    void userRegister() {
//        String userAccount ="deat4123";
//        String userPassword ="";
//        String checkPassword="123456";
//        String planetCode = "27574";
//        long result = userService.UserRegister(userAccount, userPassword, checkPassword,planetCode);
//        Assertions.assertEquals(-1,result);
//
//         userAccount ="de";
//         result = userService.UserRegister(userAccount, userPassword, checkPassword,planetCode);
//        Assertions.assertEquals(-1,result);
//
//         userAccount ="deat4123";
//         userPassword ="123456";
//         checkPassword="123456";
//         result = userService.UserRegister(userAccount, userPassword, checkPassword,planetCode);
//        Assertions.assertEquals(-1,result);
//
//        userAccount ="deat4 123";
//        result = userService.UserRegister(userAccount, userPassword, checkPassword,planetCode);
//        Assertions.assertEquals(-1,result);
//
//        userAccount ="deat4123";
//        userPassword ="123456";
//        checkPassword="12345678";
//        result = userService.UserRegister(userAccount, userPassword, checkPassword,planetCode);
//        Assertions.assertEquals(-1,result);
//
//        userAccount ="123";
//        userPassword ="12345678";
//        checkPassword="12345678";
//        result = userService.UserRegister(userAccount, userPassword, checkPassword,planetCode);
//        Assertions.assertEquals(-1,result);
//
//        userAccount ="yupi";
//        userPassword ="12345678";
//        checkPassword="12345678";
//        result = userService.UserRegister(userAccount, userPassword, checkPassword,planetCode);
//        Assertions.assertTrue(result>0);
//    }
}