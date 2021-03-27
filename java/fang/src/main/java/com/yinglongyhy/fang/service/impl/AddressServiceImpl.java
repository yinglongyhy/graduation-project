package com.yinglongyhy.fang.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yinglongyhy.fang.dto.AddressParamDto;
import com.yinglongyhy.fang.entity.Address;
import com.yinglongyhy.fang.mapper.AddressMapper;
import com.yinglongyhy.fang.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ParameterMetaData;

/**
 * <p>
 * 地址表 服务实现类
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public Page<Address> page(Page<Address> page, AddressParamDto param) {
        return addressMapper.page(page, param);
    }
}
