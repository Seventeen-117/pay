package com.jeequan.jeepay.core.ssoservice;




import com.jeequan.jeepay.core.dao.SSOUserSystemDao;
import com.jeequan.jeepay.core.entity.SSOUserSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SSOUserSystemService {

    @Autowired
    private SSOUserSystemDao userSystemDao;

    public List<SSOUserSystem> getUserSystems(Long userId) {
        return userSystemDao.findByUserId(userId);
    }
}