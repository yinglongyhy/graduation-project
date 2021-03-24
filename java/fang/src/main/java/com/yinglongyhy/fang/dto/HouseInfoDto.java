package com.yinglongyhy.fang.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "HouseInfoDto")
public class HouseInfoDto {
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

    @ApiModelProperty(value = "房屋图片")
    private List<String> pictureList;

}
