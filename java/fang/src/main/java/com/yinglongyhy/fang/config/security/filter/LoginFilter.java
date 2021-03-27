package com.yinglongyhy.fang.config.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yinglongyhy.fang.config.security.token.NameToken;
import com.yinglongyhy.fang.config.security.token.PhoneToken;
import com.yinglongyhy.fang.config.security.user.SecurityUser;
import com.yinglongyhy.fang.entity.LoginToken;
import com.yinglongyhy.fang.entity.User;
import com.yinglongyhy.fang.exception.RestApiException;
import com.yinglongyhy.fang.mapper.LoginTokenMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final LoginTokenMapper loginTokenMapper;

    public LoginFilter(AuthenticationManager authenticationManager, LoginTokenMapper loginTokenMapper) {
        this.loginTokenMapper = loginTokenMapper;
        this.setAuthenticationManager(authenticationManager);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {
        User user = new ObjectMapper().readValue(httpServletRequest.getInputStream(), User.class);
        UsernamePasswordAuthenticationToken token = null;
        if (StringUtils.isBlank(user.getPassword())) {
            log.warn("密码为空 : {}", user);
            throw new RestApiException("LoginFilter", "password is null", "密码为空");
        }
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        if (StringUtils.isNotBlank(user.getName())) {
            token = new NameToken(user.getName(), user.getPassword(), authorities);
        } else if (StringUtils.isNotBlank(user.getPhoneNum())) {
            token = new PhoneToken(user.getPhoneNum(), user.getPassword(), authorities);
        }

        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = UUID.randomUUID().toString();
        User user = ((SecurityUser) authResult.getPrincipal()).getCurrentUserInfo();
        LoginToken loginToken = new LoginToken(token, user.getId(), user.getName(), user.getRole(), request.getRemoteHost());
        int insert = loginTokenMapper.insert(loginToken);
        if (insert != 1) {
            log.warn("插入token异常 : {}", loginToken);
            throw new RestApiException("LoginFilter", "insert token failed", "密码为空");
        }
        response.addHeader("token", token);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        JSONObject o = new JSONObject();
        o.put("id", user.getId());
        o.put("name", user.getName());
        o.put("phoneNum", user.getPhoneNum());
        o.put("role", user.getRole());
        writer.write(o.toString());
    }
}
