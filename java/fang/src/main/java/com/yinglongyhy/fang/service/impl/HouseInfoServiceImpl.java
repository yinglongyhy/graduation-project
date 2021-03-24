package com.yinglongyhy.fang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yinglongyhy.fang.dto.HouseInfoDto;
import com.yinglongyhy.fang.entity.HouseInfo;
import com.yinglongyhy.fang.entity.Picture;
import com.yinglongyhy.fang.exception.RestApiException;
import com.yinglongyhy.fang.mapper.HouseInfoMapper;
import com.yinglongyhy.fang.mapper.PictureMapper;
import com.yinglongyhy.fang.service.IHouseInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 房源信息表 服务实现类
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Slf4j
@Service
public class HouseInfoServiceImpl extends ServiceImpl<HouseInfoMapper, HouseInfo> implements IHouseInfoService {
    @Autowired
    private PictureMapper pictureMapper;

    @Override
    public void save(HouseInfoDto houseInfoDto) {
        List<Picture> pictureList = pictureMapper.selectList(new QueryWrapper<Picture>().in("name", houseInfoDto.getPictureList()));
        if (pictureList.size() != houseInfoDto.getPictureList().size()) {
            log.warn("图片数量异常:{}", houseInfoDto);
            throw new RestApiException("HouseInfoServiceImpl", "picture num error", "图片数量异常");
        }
        for (Picture picture : pictureList) {

        }
    }
}
