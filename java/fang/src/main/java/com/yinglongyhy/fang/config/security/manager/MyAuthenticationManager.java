package com.yinglongyhy.fang.config.security.manager;

import org.springframework.security.authentication.ProviderManager;

//@Component
public class MyAuthenticationManager extends ProviderManager {

//    @Autowired
//    private MyAuthenticationProvider authenticationProvider;

//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        Authentication result = authenticationProvider.authenticate(authentication);
//        if (Objects.isNull(result)) {
//            throw new ProviderNotFoundException("认证失败!");
//        }
//        return result;
//    }
}
