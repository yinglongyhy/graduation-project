package com.yinglongyhy.fang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yinglongyhy.fang.dto.AddressParamDto;
import com.yinglongyhy.fang.entity.Address;

/**
 * <p>
 * 地址表 服务类
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
public interface IAddressService extends IService<Address> {
    Page<Address> page(Page<Address> page, AddressParamDto param);
}
