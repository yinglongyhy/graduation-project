package com.yinglongyhy.fang.config.security.filter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yinglongyhy.fang.entity.LoginToken;
import com.yinglongyhy.fang.entity.User;
import com.yinglongyhy.fang.mapper.LoginTokenMapper;
import com.yinglongyhy.fang.mapper.UserMapper;
import com.yinglongyhy.fang.threadlocal.TokenThreadLocal;
import com.yinglongyhy.fang.threadlocal.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class AuthenticationFilter extends BasicAuthenticationFilter {

    private static final int EXPIRATION_DAYS = 7;
    private final LoginTokenMapper loginTokenMapper;
    private final UserMapper userMapper;

    public AuthenticationFilter(AuthenticationManager authenticationManager, LoginTokenMapper loginTokenMapper, UserMapper userMapper) {
        super(authenticationManager);
        this.loginTokenMapper = loginTokenMapper;
        this.userMapper = userMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        TokenThreadLocal.remove();
        UserThreadLocal.remove();
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            chain.doFilter(request, response);
            return;
        }
        LoginToken loginToken = loginTokenMapper.selectOne(new QueryWrapper<LoginToken>().eq("token", token));
        // 检查loginToken是否为空，登陆ip是否改变，登陆信息是否过期
        if (Objects.isNull(loginToken) 
                || !request.getRemoteHost().equals(loginToken.getIp()) 
                || Duration.between(LocalDateTime.now(), loginToken.getModifiedTime()).toDays() > EXPIRATION_DAYS) {
            chain.doFilter(request, response);
            return;
        }
        User user = userMapper.selectById(loginToken.getUserId());
        UserThreadLocal.set(user);
        TokenThreadLocal.set(loginToken);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(loginToken.getRole()));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(loginToken.getUsername(), null, grantedAuthorities));
        chain.doFilter(request, response);
    }
}
