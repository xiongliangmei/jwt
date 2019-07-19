package com.runke.service;

public interface IUserService {
    /**
     * 用户登录放回jwt token
     * @param username
     * @param password
     * @return
     */
    String login(String username,String password);
}
