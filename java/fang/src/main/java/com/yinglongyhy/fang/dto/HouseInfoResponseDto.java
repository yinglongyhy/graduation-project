package com.yinglongyhy.fang.dto;

import com.yinglongyhy.fang.entity.Address;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "HouseInfoResponseDto")
public class HouseInfoResponseDto {

    @ApiModelProperty(value = "房屋id")
    private Long id;

    @ApiModelProperty(value = "所有者")
    private String owner;

    @ApiModelProperty(value = "联系电话")
    private String phoneNum;

    @ApiModelProperty(value = "租约id")
    private Long leaseId;

    @ApiModelProperty(value = "租客")
    private String tenant;

    @ApiModelProperty(value = "租客租房密钥")
    private String leaseKey;

    @ApiModelProperty(value = "协议租金，x元/月")
    private Integer leaseRent;

    @ApiModelProperty(value = "地址")
    private Address address;

    @ApiModelProperty(value = "详细地址")
    private String detailedAddress;

    @ApiModelProperty(value = "房屋的描述")
    private String description;

    @ApiModelProperty(value = "房屋的面积")
    private Integer area;

    @ApiModelProperty(value = "租金，x元/月")
    private Integer rent;

    @ApiModelProperty(value = "出租标志，1：已出租，0：未出租")
    private Integer rented;

    @ApiModelProperty(value = "房屋图片")
    private List<String> pictureList;

    @ApiModelProperty(value = "")
    private List<String> labelList;
}
