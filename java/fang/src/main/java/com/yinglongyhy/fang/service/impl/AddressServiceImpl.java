package com.yinglongyhy.fang.service.impl;

import com.yinglongyhy.fang.entity.Address;
import com.yinglongyhy.fang.mapper.AddressMapper;
import com.yinglongyhy.fang.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地址表 服务实现类
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}
