package com.yinglongyhy.fang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinglongyhy.fang.dto.AddressParamDto;
import com.yinglongyhy.fang.entity.Address;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 地址表 Mapper 接口
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Repository
public interface AddressMapper extends BaseMapper<Address> {

    Page<Address> page(Page<Address> page, AddressParamDto param);
}
