package com.jeequan.jeepay.core.ssoservice;


import com.jeequan.jeepay.core.dao.SSOLogDao;
import com.jeequan.jeepay.core.entity.SSOLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SSOLogService {

    @Autowired
    private SSOLogDao logDao;

    public void logAction(Long userId, String actionType, String description) {
        SSOLog log = new SSOLog();
        log.setUserId(userId);
        log.setActionType(actionType);
        log.setDescription(description);
        logDao.insertLog(log);
    }
}