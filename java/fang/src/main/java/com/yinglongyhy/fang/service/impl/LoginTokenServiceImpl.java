package com.yinglongyhy.fang.service.impl;

import com.yinglongyhy.fang.entity.LoginToken;
import com.yinglongyhy.fang.mapper.LoginTokenMapper;
import com.yinglongyhy.fang.service.ILoginTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录令牌表 服务实现类
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Service
public class LoginTokenServiceImpl extends ServiceImpl<LoginTokenMapper, LoginToken> implements ILoginTokenService {

}
