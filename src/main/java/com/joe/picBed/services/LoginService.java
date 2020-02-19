package com.joe.picBed.services;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by joe on 2020/2/19
 */
public interface LoginService {

    boolean hasLogin(HttpServletRequest request);

    boolean verifyLogin(String user, String pw);
}
