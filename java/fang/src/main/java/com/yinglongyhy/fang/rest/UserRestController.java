package com.yinglongyhy.fang.rest;


import com.yinglongyhy.fang.entity.LoginToken;
import com.yinglongyhy.fang.entity.User;
import com.yinglongyhy.fang.enums.RoleEnum;
import com.yinglongyhy.fang.exception.RestApiException;
import com.yinglongyhy.fang.mapper.LoginTokenMapper;
import com.yinglongyhy.fang.service.IUserService;
import com.yinglongyhy.fang.threadlocal.TokenThreadLocal;
import com.yinglongyhy.fang.threadlocal.UserThreadLocal;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserRestController {
    @Autowired
    private IUserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private LoginTokenMapper loginTokenMapper;

    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "根据用户名和密码登录，只经过filter，不会到这里")
    public ResponseEntity<User> login(@RequestBody User user) {
        user = UserThreadLocal.get();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping()
    @ApiOperation(value = "创建用户", notes = "创建用户")
    public ResponseEntity<User> create(@RequestBody User user) {
        User userThreadLocal = UserThreadLocal.get();
        if (RoleEnum.ADMIN.toString().equals(user.getRole()) && !(Objects.nonNull(userThreadLocal) && RoleEnum.ADMIN.toString().equals(userThreadLocal.getRole()))) {
            log.warn("创建用户失败 : {}", user);
            throw new RestApiException("UserRestController", "no permission", "权限不足");
        }
        if (Objects.isNull(userThreadLocal) && StringUtils.isBlank(user.getRole())) {
            user.setRole(RoleEnum.USER.toString());
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        boolean save = userService.save(user);
        if (!save) {
            log.warn("创建用户失败 : {}", user);
            throw new RestApiException("UserRestController", "create user failed", "创建用户失败");
        }

        user = userService.getById(user.getId());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping()
    @ApiOperation(value = "", notes = "")
    public ResponseEntity update(@RequestBody User user) {
        User userThreadLocal = UserThreadLocal.get();
        user.setPhoneNum(userThreadLocal.getPhoneNum());
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    @ApiOperation(value = "退出登陆", notes = "退出登陆")
    public ResponseEntity<String> logout() {
        LoginToken loginToken = TokenThreadLocal.get();
        int delete = loginTokenMapper.deleteById(loginToken.getId());
        if (delete != 1) {
            throw new RestApiException("UserRestController", "delete token failed", "删除token失败");
        }
        return new ResponseEntity<>("退出登陆成功", HttpStatus.OK);
    }
}
