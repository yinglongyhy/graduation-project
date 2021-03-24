package com.yinglongyhy.fang.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yinglongyhy
 * @since 2021-03-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Picture2houseInfo对象", description="")
public class Picture2houseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "图片，关联picture表")
    private Long picture;

    @ApiModelProperty(value = "房源信息，关联house_info表")
    private Long houseInfo;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifiedTime;

    @ApiModelProperty(value = "删除标记，1：已删除，0：未删除")
    @TableLogic
    private Integer deleted;


}
