package com.yinglongyhy.fang.service.impl;

import com.yinglongyhy.fang.entity.Label;
import com.yinglongyhy.fang.mapper.LabelMapper;
import com.yinglongyhy.fang.service.ILabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label> implements ILabelService {

}
