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
 * 登录令牌表
 * </p>
 *
 * @author yinglongyhy
 * @since 2020-12-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="LoginToken对象", description="登录令牌表")
public class LoginToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "登录令牌")
    private String token;

    @ApiModelProperty(value = "登录用户的id，关联user表")
    private Long userId;

    @ApiModelProperty(value = "登录的用户名")
    private String username;

    @ApiModelProperty(value = "角色，'admin'：管理员，'user'：用户")
    private String role;

    @ApiModelProperty(value = "登录ip，当登录ip改变的时候，认证不通过")
    private String ip;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifiedTime;

    @ApiModelProperty(value = "删除标志，1：已删除，0：未删除")
    @TableLogic
    private Integer deleted;

    public LoginToken(String token, Long userId, String username, String role, String ip) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.ip = ip;
    }
}
