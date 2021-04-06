package com.yinglongyhy.fang.rest;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinglongyhy.fang.entity.Address;
import com.yinglongyhy.fang.entity.LoginToken;
import com.yinglongyhy.fang.entity.Picture;
import com.yinglongyhy.fang.entity.User;
import com.yinglongyhy.fang.enums.RoleEnum;
import com.yinglongyhy.fang.exception.RestApiException;
import com.yinglongyhy.fang.mapper.LoginTokenMapper;
import com.yinglongyhy.fang.service.IUserService;
import com.yinglongyhy.fang.threadlocal.TokenThreadLocal;
import com.yinglongyhy.fang.threadlocal.UserThreadLocal;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

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
        user.setLeaseKey(UUID.randomUUID().toString());
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
        } else {
            user.setPassword(null);
        }
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    @ApiOperation(value = "获取用户信息", notes = "")
    public ResponseEntity<User> info() {
        User user = UserThreadLocal.get();
        if (Objects.isNull(user)) {
            log.warn("用户信息不存在");
            throw new RestApiException("UserRestController", "user not found", "用户信息不存在");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
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

    @GetMapping("/page")
    @ApiOperation(value = "获取用户分页", notes = "")
    public ResponseEntity<Page<User>> page(
            @ModelAttribute @ApiParam(value = "params") User params,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") @ApiParam(value = "pageNumber", defaultValue = "1") Integer pageNumber
            , @RequestParam(value = "pageSize", required = false, defaultValue = "10") @ApiParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (Objects.nonNull(params.getName())) {
            wrapper = wrapper.like("name", "%" + params.getName() + "%");
        }
        if (Objects.nonNull(params.getPhoneNum())) {
            wrapper = wrapper.like("phone_num", "%" + params.getPhoneNum() + "%");
        }
        if (Objects.nonNull(params.getRole())) {
            wrapper = wrapper.like("role", "%" + params.getRole() + "%");
        }
        Page<User> page = userService.page(new Page<User>(pageNumber, pageSize), wrapper);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity delete(@PathVariable("id") @ApiParam(value = "id") Long id) {
        userService.removeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/save")
    @ApiOperation(value = "", notes = "")
    public ResponseEntity save(@ModelAttribute @ApiParam(value = "user") User user) {
        if (StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(null);
        }
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
