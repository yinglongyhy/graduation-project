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
 * 房源信息表
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="HouseInfo对象", description="房源信息表")
public class HouseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "房屋拥有者，即房源发布者，根据发布的用户创建，关联user表")
    private Long owner;

    @ApiModelProperty(value = "区域地址，从address表中选")
    private String address;

    @ApiModelProperty(value = "房屋的详细地址，作为区域地址的补充")
    private String detailedAddress;

    @ApiModelProperty(value = "房屋的描述")
    private String description;

    @ApiModelProperty(value = "房屋的面积")
    private Integer area;

    @ApiModelProperty(value = "租金，x元/月")
    private Integer rent;

    @ApiModelProperty(value = "出租标志，1：已出租，0：未出租")
    private Integer rented;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifiedTime;

    @ApiModelProperty(value = "删除标记，1：已删除，0：未删除")
    @TableLogic
    private Integer deleted;

    public HouseInfo(Long owner, String address, String detailedAddress, String description, Integer area, Integer rent, Integer rented) {
        this.owner = owner;
        this.address = address;
        this.detailedAddress = detailedAddress;
        this.description = description;
        this.area = area;
        this.rent = rent;
        this.rented = rented;
    }
}
