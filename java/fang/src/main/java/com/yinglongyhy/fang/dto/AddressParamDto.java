package com.yinglongyhy.fang.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 地址表
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Data
@ApiModel(value="AddressParamDto", description="AddressParamDto")
public class AddressParamDto implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "中国地区编码")
    private String code;

    @ApiModelProperty(value = "中国地区名称")
    private String name;

    @ApiModelProperty(value = "中国地区全名")
    private String fullname;

}
