package com.yinglongyhy.fang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yinglongyhy.fang.dto.HouseInfoDto;
import com.yinglongyhy.fang.dto.HouseInfoParamsDto;
import com.yinglongyhy.fang.dto.HouseInfoResponseDto;
import com.yinglongyhy.fang.entity.*;
import com.yinglongyhy.fang.mapper.HouseInfoMapper;
import com.yinglongyhy.fang.mapper.LabelMapper;
import com.yinglongyhy.fang.mapper.PictureMapper;
import com.yinglongyhy.fang.service.IHouseInfoService;
import com.yinglongyhy.fang.service.ILabel2houseInfoService;
import com.yinglongyhy.fang.service.ILabelService;
import com.yinglongyhy.fang.service.IPicture2houseInfoService;
import com.yinglongyhy.fang.threadlocal.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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
@Transactional(rollbackFor = Exception.class)
public class HouseInfoServiceImpl extends ServiceImpl<HouseInfoMapper, HouseInfo> implements IHouseInfoService {
    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private HouseInfoMapper houseInfoMapper;
    @Autowired
    private LabelMapper labelMapper;
    @Autowired
    private IPicture2houseInfoService picture2houseInfoService;
    @Autowired
    private ILabelService labelService;
    @Autowired
    private ILabel2houseInfoService label2houseInfoService;

    @Override
    public void save(HouseInfoDto houseInfoDto) {

        if (Objects.isNull(houseInfoDto.getId())) {
            HouseInfo houseInfo = new HouseInfo();
            BeanUtils.copyProperties(houseInfoDto, houseInfo);
            houseInfo.setOwner(UserThreadLocal.get().getId());
            houseInfoMapper.insert(houseInfo);
            BeanUtils.copyProperties(houseInfo, houseInfoDto);
        }

        handlePicture(houseInfoDto);
        handleLabel(houseInfoDto);

    }

    private void handleLabel(HouseInfoDto houseInfoDto) {
        List<Label> labels = new ArrayList<>();
        if (!CollectionUtils.isEmpty(houseInfoDto.getLabelList())) {
            for (String labelName : houseInfoDto.getLabelList()) {
                labelService.saveOrUpdate(new Label(labelName), new UpdateWrapper<Label>().eq("name", labelName));
                Label label = labelService.getOne(new QueryWrapper<Label>().eq("name", labelName));
                labels.add(label);
            }
        }
        Map<String, Label> labelMap = labels.stream().collect(Collectors.toMap(Label::getName, Function.identity()));
        List<Label> labelList = labelMapper.selectListByHouseInfo(houseInfoDto.getId());
        List<String> labelNameList = labelList.stream().map(Label::getName).collect(Collectors.toList());
        if (Objects.isNull(houseInfoDto.getLabelList())) {
            houseInfoDto.setLabelList(new ArrayList<>());
        }
        List<String> insertLabelNameList = houseInfoDto.getLabelList().stream().filter(name -> !labelNameList.contains(name)).collect(Collectors.toList());
        List<String> deletedLabelNameList = labelNameList.stream().filter(name -> !houseInfoDto.getLabelList().contains(name)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(deletedLabelNameList)) {
            List<Label> deletedLabelList = labelMapper.selectList(new QueryWrapper<Label>().in("name", deletedLabelNameList));
            deletedLabelList.forEach(label -> label2houseInfoService.remove(new QueryWrapper<Label2houseInfo>().eq("label", label.getId()).eq("house_info", houseInfoDto.getId())));
        }
        insertLabelNameList.forEach(name -> label2houseInfoService.save(new Label2houseInfo(labelMap.get(name).getId(), houseInfoDto.getId())));
    }

    private void handlePicture(HouseInfoDto houseInfoDto) {
        if (CollectionUtils.isEmpty(houseInfoDto.getPictureList())) {
            houseInfoDto.setPictureList(new ArrayList<>());
        }
        List<Picture> pictureList = pictureMapper.selectListByHouseInfo(houseInfoDto.getId());
        List<String> pictureNameList = pictureList.stream().map(Picture::getName).collect(Collectors.toList());
        List<String> deletedPictureNameList = pictureNameList.stream().filter(pictureName -> !houseInfoDto.getPictureList().contains(pictureName)).collect(Collectors.toList());
        List<String> insertPictureNameList = houseInfoDto.getPictureList().stream().filter(pictureName -> !pictureNameList.contains(pictureName)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(deletedPictureNameList)) {
            List<Picture> deletedPictureList = pictureMapper.selectList(new QueryWrapper<Picture>().in("name", deletedPictureNameList));
            deletedPictureList.forEach(picture -> picture2houseInfoService.remove(new QueryWrapper<Picture2houseInfo>().eq("picture", picture.getId()).eq("house_info", houseInfoDto.getId())));
        }
        if (!CollectionUtils.isEmpty(insertPictureNameList)) {
            List<Picture> insertPictureList = pictureMapper.selectList(new QueryWrapper<Picture>().in("name", insertPictureNameList));
            insertPictureList.forEach(picture -> picture2houseInfoService.save(new Picture2houseInfo(picture.getId(), houseInfoDto.getId())));

        }
    }

    @Override
    public Page<HouseInfoResponseDto> page(HouseInfoParamsDto params, Integer pageNumber, Integer pageSize) {
        if (Objects.isNull(params.getRentType())) {
            params.setRentType(0);
        }
        params.setRentType(params.getRentType() != 1 ? 0 : 1);
        Page<HouseInfoResponseDto> houseInfoResponseDtoPage = houseInfoMapper.page(new Page<HouseInfoResponseDto>(pageNumber, pageSize), params);
        for (HouseInfoResponseDto record : houseInfoResponseDtoPage.getRecords()) {
            record.setPictureList(pictureMapper.selectListByHouseInfo(record.getId()).stream().map(Picture::getName).collect(Collectors.toList()));
            record.setLabelList(labelMapper.selectListByHouseInfo(record.getId()).stream().map(Label::getName).collect(Collectors.toList()));
        }

        return houseInfoResponseDtoPage;
    }

    @Override
    public void delete(Long id) {
        label2houseInfoService.remove(new QueryWrapper<Label2houseInfo>().eq("house_info", id));
        picture2houseInfoService.remove(new QueryWrapper<Picture2houseInfo>().eq("house_info", id));
        removeById(id);
    }

    @Override
    public HouseInfoResponseDto getOneById(Long id) {
        HouseInfoResponseDto houseInfoResponseDto = houseInfoMapper.getOneById(id);
        houseInfoResponseDto.setPictureList(pictureMapper.selectListByHouseInfo(houseInfoResponseDto.getId()).stream().map(Picture::getName).collect(Collectors.toList()));
        houseInfoResponseDto.setLabelList(labelMapper.selectListByHouseInfo(houseInfoResponseDto.getId()).stream().map(Label::getName).collect(Collectors.toList()));
        return houseInfoResponseDto;
    }
}
