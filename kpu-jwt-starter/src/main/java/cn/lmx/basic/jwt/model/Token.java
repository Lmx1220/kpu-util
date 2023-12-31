package cn.lmx.basic.jwt.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lmx
 * @version 1.0
 * @description: Token 信息类，用于存储 token 信息，包括 token 本身、有效期、到期时间等信息
 * @date 2023/7/4 14:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token implements Serializable {
    private static final long serialVersionUID = -8482946147572784305L;
    /**
     * token
     */
    @ApiModelProperty(value = "token")
    private String token;
    /**
     * 有效时间：单位：秒
     */
    @ApiModelProperty(value = "有效期")
    private Long expire;


    @ApiModelProperty(value = "到期时间")
    private LocalDateTime expiration;

}
