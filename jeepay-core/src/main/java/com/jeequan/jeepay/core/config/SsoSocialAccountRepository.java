package com.jeequan.jeepay.core.config;


import com.jeequan.jeepay.core.entity.SsoSocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SsoSocialAccountRepository extends JpaRepository<SsoSocialAccount, Long> {
    Optional<SsoSocialAccount> findByProviderAndProviderId(String provider, String providerId);
}
