package com.yinglongyhy.fang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yinglongyhy.fang.dto.LeaseAddRequestDto;
import com.yinglongyhy.fang.entity.HouseInfo;
import com.yinglongyhy.fang.entity.Lease;
import com.yinglongyhy.fang.entity.User;
import com.yinglongyhy.fang.exception.RestApiException;
import com.yinglongyhy.fang.mapper.LeaseMapper;
import com.yinglongyhy.fang.service.IHouseInfoService;
import com.yinglongyhy.fang.service.ILeaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yinglongyhy.fang.service.IUserService;
import com.yinglongyhy.fang.threadlocal.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * <p>
 * 租房信息（协议） 服务实现类
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class LeaseServiceImpl extends ServiceImpl<LeaseMapper, Lease> implements ILeaseService {

    @Autowired
    private IHouseInfoService houseInfoService;

    @Autowired
    private IUserService userService;

    @Override
    public Lease add(LeaseAddRequestDto leaseAddRequestDto) {
        HouseInfo houseInfo = houseInfoService.getById(leaseAddRequestDto.getHouseInfoId());
        if (Objects.isNull(leaseAddRequestDto.getRent()) || leaseAddRequestDto.getRent() <= 0) {
            log.warn("租金异常 : {}", leaseAddRequestDto);
            throw new RestApiException("LeaseServiceImpl", "", "租金异常");
        }
        if (Objects.isNull(houseInfo)) {
            log.warn("房源不存在 : {}", houseInfo);
            throw new RestApiException("LeaseServiceImpl", "houseInfo not found", "房源不存在");
        }
        if (!houseInfo.getOwner().equals(UserThreadLocal.get().getId())) {
            log.warn("用户不是房源所有人 : {}", houseInfo);
            throw new RestApiException("LeaseServiceImpl", "", "用户不是房源所有人");
        }
        if (houseInfo.getRented().equals(1)) {
            log.warn("房源已出租 : {}", houseInfo);
            throw new RestApiException("LeaseServiceImpl", "houseInfo is rented", "房源已出租");
        }
        User user = userService.getOne(new QueryWrapper<User>()
                .eq("name", leaseAddRequestDto.getTenantName()).
                        eq("lease_key", leaseAddRequestDto.getLeaseKey()));
        if (Objects.isNull(user)) {
            log.warn("用户不存在 : {}", houseInfo);
            throw new RestApiException("LeaseServiceImpl", "user not found", "用户不存在");
        }
        Lease lease = new Lease(user.getId(), houseInfo.getId(), leaseAddRequestDto.getRent(), leaseAddRequestDto.getLeaseKey());
        save(lease);
        houseInfoService.update(new UpdateWrapper<HouseInfo>().set("rented", 1).eq("id", leaseAddRequestDto.getHouseInfoId()));
        return lease;
    }

    @Override
    public void delete(Long id) {
        Lease lease = getById(id);
        removeById(id);
        houseInfoService.update(new UpdateWrapper<HouseInfo>().set("rented", 0).eq("id", lease.getHouseInfo()));
    }
}
