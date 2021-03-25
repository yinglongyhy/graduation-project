package com.yinglongyhy.fang.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "HouseInfoResponseDto")
public class HouseInfoResponseDto {

    @ApiModelProperty(value = "")
    private String owner;

    @ApiModelProperty(value = "租客，关联user表")
    private String tenant;

    @ApiModelProperty(value = "协议租金，x元/月")
    private Integer leaseRent;

    @ApiModelProperty(value = "区域地址，从address表中选")
    private String address;

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
