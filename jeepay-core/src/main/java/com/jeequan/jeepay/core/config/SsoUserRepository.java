package com.jeequan.jeepay.core.config;

import com.jeequan.jeepay.core.entity.SsoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SsoUserRepository extends JpaRepository<SsoUser, Long> {
    Optional<SsoUser> findByUsername(String username);
    Optional<SsoUser> findByEmail(String email);
}



