package com.jeequan.jeepay.core.ssocontroller;

// SSOController.java

import com.jeequan.jeepay.core.entity.SsoUser;
import com.jeequan.jeepay.core.ssoservice.SSOTokenService;
import com.jeequan.jeepay.core.ssoservice.SSOUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sso")
public class SSOController {

    @Autowired
    private SSOUserService userService;

    @Autowired
    private SSOTokenService tokenService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        SsoUser user = userService.authenticate(username, password);
        if (user == null) {
            return "用户名或密码错误";
        }
        if (userService.isUserLocked(user.getUserId())) {
            return "账户已锁定，请稍后再试";
        }
        String token = tokenService.generateToken(user.getUserId());
        return token;
    }

    @PostMapping("/logout")
    public String logout(@RequestParam String token) {
        tokenService.invalidateToken(token);
        return "登出成功";
    }
}