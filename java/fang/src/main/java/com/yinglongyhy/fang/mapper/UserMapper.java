package com.yinglongyhy.fang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yinglongyhy.fang.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     *
     * @param user
     */
    void update(@Param("user") User user);
}
