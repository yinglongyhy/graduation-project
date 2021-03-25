package com.yinglongyhy.fang.service;

import com.yinglongyhy.fang.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
public interface IUserService extends IService<User> {

    void update(User user);
}
