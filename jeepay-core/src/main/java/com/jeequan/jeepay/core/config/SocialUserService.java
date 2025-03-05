package com.jeequan.jeepay.core.config;

import com.jeequan.jeepay.core.entity.SsoSocialAccount;
import com.jeequan.jeepay.core.entity.SsoUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SocialUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {
    private final SsoUserRepository userRepository;
    private final SsoSocialAccountRepository socialAccountRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = new DefaultOidcUser(
                AuthorityUtils.NO_AUTHORITIES,
                userRequest.getIdToken()
        );

        String email = oidcUser.getEmail();
        SsoUser user = userRepository.findByEmail(email)
                .orElseGet(() -> createNewUser(oidcUser));

        linkSocialAccount(user, userRequest);

        return new CustomOidcUser(oidcUser, user.getUserId(), getAuthorities(user.getUserId()));
    }

    private SsoUser createNewUser(OidcUser oidcUser) {
        SsoUser user = new SsoUser();
        user.setUsername(oidcUser.getEmail());
        user.setPassword(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    private void linkSocialAccount(SsoUser user, OidcUserRequest userRequest) {
        socialAccountRepository.findByProviderAndProviderId(
                userRequest.getClientRegistration().getRegistrationId(),
                oidcUser.getName()
        ).ifPresentOrElse(
                existing -> {},
                () -> socialAccountRepository.save(new SsoSocialAccount(
                        user.getUserId(),
                        userRequest.getClientRegistration().getRegistrationId(),
                        oidcUser.getName()
                ))
        );
    }
}