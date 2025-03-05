package com.jeequan.jeepay.core.dao;

import com.jeequan.jeepay.core.entity.SSOSystem;

public interface SSOSystemDao {
    SSOSystem findByDomainName(String domainName);
}
