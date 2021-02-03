package com.yinglongyhy.fang.service.impl;

import com.yinglongyhy.fang.entity.User;
import com.yinglongyhy.fang.mapper.UserMapper;
import com.yinglongyhy.fang.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
