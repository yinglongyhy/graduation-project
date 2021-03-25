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
}
