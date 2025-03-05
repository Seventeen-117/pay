package com.jeequan.jeepay.core.config;

import com.jeequan.jeepay.core.entity.SSOUserSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SsoUserSystemRepository extends JpaRepository<SSOUserSystem, Long> {
    List<SSOUserSystem> findByUserId(Long userId);
}

