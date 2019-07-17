package com.runke.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("admin/v1")
@Slf4j
public class UserController {

    @RequestMapping(value = "/login")
    public  String login(HttpServletRequest request){
        log.info(request.getHeader("header"));
        return request.getHeader("header");
    }
}
