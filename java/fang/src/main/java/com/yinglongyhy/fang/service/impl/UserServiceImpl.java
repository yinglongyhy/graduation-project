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
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void update(User user) {
        Integer count = userMapper.selectCount(new QueryWrapper<User>().eq("name", user.getName()));
        User userInfo = userMapper.selectOne(new QueryWrapper<User>().eq("phone_num", user.getPhoneNum()));
        if (count != 0 && (count > 1 || !(count == 1 && userInfo.getName().equals(user.getName())))) {
            log.warn("修改用户失败 : {}", user);
            throw new RestApiException("UserRestController", "update user failed", "修改用户失败,用户名已存在");
        }
        userMapper.update(user);
    }
}
