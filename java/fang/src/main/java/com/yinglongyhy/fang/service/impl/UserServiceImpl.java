package com.yinglongyhy.fang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yinglongyhy.fang.entity.User;
import com.yinglongyhy.fang.exception.RestApiException;
import com.yinglongyhy.fang.mapper.UserMapper;
import com.yinglongyhy.fang.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void update(User user) {
        if (userMapper.selectCount(new QueryWrapper<User>().eq("name", user.getName())) > 0) {
            log.warn("修改用户失败 : {}", user);
            throw new RestApiException("UserRestController", "update user failed", "修改用户失败,用户名已存在");
        }
        userMapper.update(user);
    }
}
