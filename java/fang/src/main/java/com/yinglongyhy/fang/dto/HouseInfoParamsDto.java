package com.yinglongyhy.fang.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "HouseInfoParamsDto")
public class HouseInfoParamsDto {
    @ApiModelProperty(value = "")
    private String owner;

    @ApiModelProperty(value = "租客，关联user表")
    private String tenant;

    @ApiModelProperty(value = "租金-最低")
    private Integer rentMin;

    @ApiModelProperty(value = "租金-最高")
    private Integer rentMax;

    @ApiModelProperty(value = "出租标志，1：已出租，0：未出租")
    private Integer rented;
}
