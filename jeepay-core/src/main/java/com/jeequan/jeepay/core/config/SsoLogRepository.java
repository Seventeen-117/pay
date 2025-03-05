package com.jeequan.jeepay.core.config;

import com.jeequan.jeepay.core.entity.SSOLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SsoLogRepository extends JpaRepository<SSOLog, Long> {
}