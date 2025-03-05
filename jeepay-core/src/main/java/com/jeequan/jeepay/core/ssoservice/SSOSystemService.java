package com.jeequan.jeepay.core.ssoservice;


import com.jeequan.jeepay.core.dao.SSOSystemDao;
import com.jeequan.jeepay.core.entity.SSOSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SSOSystemService {

    @Autowired
    private SSOSystemDao systemDao;

    public SSOSystem getSystemByDomainName(String domainName) {
        return systemDao.findByDomainName(domainName);
    }
}

