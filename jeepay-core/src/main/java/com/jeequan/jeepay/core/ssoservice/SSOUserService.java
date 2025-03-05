package com.jeequan.jeepay.core.ssoservice;


import com.jeequan.jeepay.core.dao.SSOUserDao;
import com.jeequan.jeepay.core.entity.SSOUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SSOUserService {

    @Autowired
    private SSOUserDao userDao;

    public SSOUser authenticate(String username, String password) {
        SSOUser user = userDao.findByUsername(username);
        if (user == null ||!user.getPassword().equals(password)) {
            // 记录登录失败
            userDao.insertLoginAttempt(user != null? user.getUserId() : null, "", false);
            // 检查是否需要锁定账户
            if (user != null) {
                int failedAttempts = userDao.countFailedAttempts(user.getUserId(), LocalDateTime.now().minusHours(1));
                if (failedAttempts >= 3) {
                    LocalDateTime lockUntil = LocalDateTime.now().plusHours(1);
                    userDao.lockUser(user.getUserId(), lockUntil);
                }
            }
            return null;
        }
        // 记录登录成功
        userDao.insertLoginAttempt(user.getUserId(), "", true);
        // 重置失败次数
        user.setFailedAttempts(0);
        userDao.updateUser(user);
        return user;
    }

    public boolean isUserLocked(Long userId) {
        SSOUser user = userDao.findByUsername(null);
        if (user == null) {
            return false;
        }
        return user.isLocked() && user.getLockUntil().isAfter(LocalDateTime.now());
    }
}