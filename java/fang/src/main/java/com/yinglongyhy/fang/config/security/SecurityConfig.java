package com.yinglongyhy.fang.config.security;

import com.yinglongyhy.fang.config.security.filter.AuthenticationFilter;
import com.yinglongyhy.fang.config.security.filter.LoginFilter;
import com.yinglongyhy.fang.mapper.LoginTokenMapper;
import com.yinglongyhy.fang.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginTokenMapper loginTokenMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers("/**/*").permitAll()
                .antMatchers(HttpMethod.POST, "/user").permitAll()
                .antMatchers(HttpMethod.DELETE, "/user/logout").hasAuthority("admin")
//                .antMatchers("/books", "/books/**").permitAll()
//                .antMatchers("/bookCategories", "/bookCategories/**").permitAll()
//                .antMatchers(HttpMethod.POST, "/admins").permitAll()
//                .antMatchers(HttpMethod.POST, "/admins/login").permitAll()
//                .antMatchers(HttpMethod.POST, "/users").permitAll()
//                .antMatchers(HttpMethod.POST, "/users/login").permitAll()
//                .antMatchers(HttpMethod.POST, "/users").permitAll()
//                .antMatchers("/admins", "/admins/*", "/admins/**").hasAuthority("ADMIN")
//                .antMatchers("/users", "/users/*", "/users/**").hasAuthority("USER")
                // swagger start
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/configuration/security").permitAll()
                // swagger end
//                .anyRequest().permitAll()
                .anyRequest().authenticated()
                .and()
                // 自定义过滤器认证用户名密码
                .addFilterAt(getLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilter(new AuthenticationFilter(authenticationManager(), loginTokenMapper, userMapper));

    }

    private LoginFilter getLoginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter(authenticationManager(), loginTokenMapper);
        loginFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/**/login", "POST"));
        return loginFilter;
    }

    /**
     * 注入BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
