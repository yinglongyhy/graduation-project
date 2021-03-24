package com.yinglongyhy.fang.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("HouseAddRequestDto")
public class HouseAddRequestDto {
    @ApiModelProperty(value = "房屋拥有者，即房源发布者，根据发布的用户创建")
    private Long owner;

    @ApiModelProperty(value = "区域地址")
    private String address;

    @ApiModelProperty(value = "房屋的详细地址，作为区域地址的补充")
    private String detailedAddress;

    @ApiModelProperty(value = "房屋的描述")
    private String description;

    @ApiModelProperty(value = "房屋的面积")
    private Integer area;

    @ApiModelProperty(value = "租金，x元/月")
    private Integer rent;

    private List<String> lableList;

    private List<>
}
