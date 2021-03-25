package com.yinglongyhy.fang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yinglongyhy.fang.entity.Label;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Repository
public interface LabelMapper extends BaseMapper<Label> {

    List<Label> selectListByHouseInfo(Long houseInfoId);
}
