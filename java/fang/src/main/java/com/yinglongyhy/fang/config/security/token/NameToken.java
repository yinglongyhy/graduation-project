package com.yinglongyhy.fang.config.security.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class NameToken extends UsernamePasswordAuthenticationToken {
    public NameToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public NameToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
