package com.yinglongyhy.fang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 租房信息（协议）
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Lease对象", description="租房信息（协议）")
public class Lease implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "租客，关联user表")
    private Long tenant;

    @ApiModelProperty(value = "房源信息，关联house_info表")
    private Long houseInfo;

    @ApiModelProperty(value = "租金，x元/月")
    private Integer rent;

    @ApiModelProperty(value = "租房密钥，房东用租户的密钥创建租约")
    private String leaseKey;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifiedTime;

    @ApiModelProperty(value = "删除标记，1：已删除，0：未删除")
    @TableLogic
    private Integer deleted;

    public Lease(Long tenant, Long houseInfo, Integer rent, String leaseKey) {
        this.tenant = tenant;
        this.houseInfo = houseInfo;
        this.rent = rent;
        this.leaseKey = leaseKey;
    }
}
