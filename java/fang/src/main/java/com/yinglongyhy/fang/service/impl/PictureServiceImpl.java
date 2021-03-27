package com.yinglongyhy.fang.service.impl;

import com.yinglongyhy.fang.entity.Picture;
import com.yinglongyhy.fang.mapper.PictureMapper;
import com.yinglongyhy.fang.service.IPictureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 图片表 服务实现类
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements IPictureService {

}
