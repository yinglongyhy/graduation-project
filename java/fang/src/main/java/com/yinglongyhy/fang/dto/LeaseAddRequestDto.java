package com.yinglongyhy.fang.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "HouseInfoDto")
public class LeaseAddRequestDto {

    @ApiModelProperty(value = "租约id")
    private String LeaseId;

    @ApiModelProperty(value = "房源id")
    private String houseInfoId;

    @ApiModelProperty(value = "租客用户名")
    private String tenantName;

    @ApiModelProperty(value = "租客租房密钥")
    private String leaseKey;

    @ApiModelProperty(value = "租金，x元/月")
    private Integer rent;

}
