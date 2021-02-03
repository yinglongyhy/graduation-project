package com.yinglongyhy.fang.service.impl;

import com.yinglongyhy.fang.entity.Video;
import com.yinglongyhy.fang.mapper.VideoMapper;
import com.yinglongyhy.fang.service.IVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 视频表 服务实现类
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {

}
