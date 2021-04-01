package com.yinglongyhy.fang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinglongyhy.fang.dto.HouseInfoParamsDto;
import com.yinglongyhy.fang.dto.HouseInfoResponseDto;
import com.yinglongyhy.fang.entity.HouseInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 房源信息表 Mapper 接口
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Repository
public interface HouseInfoMapper extends BaseMapper<HouseInfo> {

    Page<HouseInfoResponseDto> page(Page<HouseInfoResponseDto> page, @Param(value = "params") HouseInfoParamsDto params);

    HouseInfoResponseDto getOneById(Long id);
}
