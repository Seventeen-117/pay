package com.jeequan.jeepay.core.config;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
 @Service
 public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

        @Override
        public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
            OidcUser oidcUser = new DefaultOidcUser(
                    getAuthorities(userRequest),
                    userRequest.getIdToken(),
                    "sub"
            );

            // 检查或创建本地用户
            String email = oidcUser.getAttribute("email");
            SsoUser user = userRepository.findByEmail(email)
                    .orElseGet(() -> createUserFromOidc(oidcUser));

            return new CustomOidcUser(oidcUser, user.getUserId());
        }
 }
