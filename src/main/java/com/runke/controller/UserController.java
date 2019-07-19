package com.runke.controller;

import com.runke.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("admin/user")
@Slf4j
public class UserController {

    @Autowired
    IUserService userService;

    /**
     * 用户登录
     * @param
     * @return
     */
    @RequestMapping(value = "/login")
    public  String login(String username,String password){
        return userService.login(username,password);
    }
    @RequestMapping(value = "/getUserInfo")
    public String getUserInfo(HttpServletRequest request){
        return request.getHeader("token");
    }

    @RequestMapping(value = "/hi")
    public String hi(HttpServletRequest request){
       return "hi";
    }
}
