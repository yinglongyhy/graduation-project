package com.yinglongyhy.fang.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinglongyhy.fang.dto.HouseInfoDto;
import com.yinglongyhy.fang.dto.HouseInfoParamsDto;
import com.yinglongyhy.fang.dto.HouseInfoResponseDto;
import com.yinglongyhy.fang.entity.HouseInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 房源信息表 服务类
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
public interface IHouseInfoService extends IService<HouseInfo> {

    void save(HouseInfoDto houseInfoDto);

    Page<HouseInfoResponseDto> page(HouseInfoParamsDto params, Integer pageNumber, Integer pageSize);

    void delete(Long id);

    HouseInfoResponseDto getOneById(Long id);
}
