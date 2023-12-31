package cn.lmx.basic.jwt.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author lmx
 * @version 1.0
 * @description: AuthInfo
 * @date 2023/7/4 14:27
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "认证信息")
public class AuthInfo {
    @ApiModelProperty(value = "令牌")
    private String token;
    @ApiModelProperty(value = "令牌类型")
    private String tokenType;
    @ApiModelProperty(value = "刷新令牌")
    private String refreshToken;
    @ApiModelProperty(value = "用户名")
    private String nickName;
    @ApiModelProperty(value = "账号名")
    private String username;
    @ApiModelProperty(value = "头像")
    private Long avatarId;
    @ApiModelProperty(value = "工作描述")
    private String workDescribe;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "过期时间（秒）")
    private long expire;
    @ApiModelProperty(value = "到期时间")
    private LocalDateTime expiration;
    @ApiModelProperty(value = "有效期")
    private Long expireMillis;
}
