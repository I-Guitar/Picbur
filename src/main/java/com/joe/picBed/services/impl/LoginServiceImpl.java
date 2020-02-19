package com.joe.picBed.services.impl;

import com.joe.picBed.entity.ConfigEntry;
import com.joe.picBed.entity.constant.Constants;
import com.joe.picBed.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by joe on 2020/2/19
 */
@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    private ConfigEntry configEntry;

    @Override
    public boolean hasLogin(HttpServletRequest request) {
        final Cookie cookie = getCookieByName(request, Constants.LOGIN_COOKIE_NAME);
        // TODO 一些操作策略
        return cookie != null;
    }

    @Override
    public boolean verifyLogin(String user, String pw) {
        return configEntry.getUser().equals(user) && configEntry.getPassword().equals(pw);
    }

    /**
     * 通过名称获取cookie
     */
    private Cookie getCookieByName(HttpServletRequest request, String cookieName) {
        for (Cookie cookie : request.getCookies()) {
            if (cookieName.equals(cookie.getName())) {
                // TODO 验证cookie有效时间
                return cookie;
            }
        }
        return null;
    }

}
