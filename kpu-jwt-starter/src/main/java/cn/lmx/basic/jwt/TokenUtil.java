package cn.lmx.basic.jwt;

import cn.hutool.core.convert.Convert;
import cn.lmx.basic.jwt.model.AuthInfo;
import cn.lmx.basic.jwt.model.JwtUserInfo;
import cn.lmx.basic.jwt.model.Token;
import cn.lmx.basic.jwt.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cn.lmx.basic.context.ContextConstants.*;

/**
 * @author lmx
 * @version 1.0
 * @description: 认证工具类
 * @date 2023/7/4 14:27
 */

@AllArgsConstructor
public class TokenUtil {

    /**
     * 认证服务端使用，如 authority-server
     * 生成和 解析token
     */
    private final JwtProperties jwtProperties;


    /**
     * 创建认证token
     *
     * @param userInfo 用户信息
     * @return token
     */
    public AuthInfo createAuthInfo(JwtUserInfo userInfo, Long expireMillis) {
        if (expireMillis == null || expireMillis <= 0) {
            expireMillis = jwtProperties.getExpire();
        }

        //设置jwt参数
        Map<String, String> param = new HashMap<>(16);
        param.put(JWT_KEY_TOKEN_TYPE, BEARER_HEADER_KEY);
        param.put(JWT_KEY_USER_ID, Convert.toStr(userInfo.getUserId(), "0"));
        param.put(JWT_KEY_USERNAME, userInfo.getUsername());
        param.put(JWT_KEY_NICK_NAME, userInfo.getNickName());

        Token token = JwtUtil.createJwt(param, expireMillis);

        AuthInfo authInfo = new AuthInfo();
        authInfo.setUsername(userInfo.getUsername());
        authInfo.setNickName(userInfo.getNickName());
        authInfo.setUserId(userInfo.getUserId());
        authInfo.setTokenType(BEARER_HEADER_KEY);
        authInfo.setToken(token.getToken());
        authInfo.setExpire(token.getExpire());
        authInfo.setExpiration(token.getExpiration());
        authInfo.setRefreshToken(createRefreshToken(userInfo).getToken());
        authInfo.setExpireMillis(expireMillis);
        return authInfo;
    }

    /**
     * 创建refreshToken
     *
     * @param userInfo 用户信息
     * @return refreshToken
     */
    private Token createRefreshToken(JwtUserInfo userInfo) {
        Map<String, String> param = new HashMap<>(16);
        param.put(JWT_KEY_TOKEN_TYPE, REFRESH_TOKEN_KEY);
        param.put(JWT_KEY_USER_ID, Convert.toStr(userInfo.getUserId(), "0"));
        return JwtUtil.createJwt(param, jwtProperties.getRefreshExpire());
    }

    /**
     * 解析token
     *
     * @param token token
     * @return 用户信息
     */
    public AuthInfo getAuthInfo(String token) {
        Claims claims = JwtUtil.getClaims(token, jwtProperties.getAllowedClockSkewSeconds());
        String tokenType = Convert.toStr(claims.get(JWT_KEY_TOKEN_TYPE));
        Long userId = Convert.toLong(claims.get(JWT_KEY_USER_ID));
        String username = Convert.toStr(claims.get(JWT_KEY_USERNAME));
        String nickName = Convert.toStr(claims.get(JWT_KEY_NICK_NAME));
        Date expiration = claims.getExpiration();
        return new AuthInfo().setToken(token)
                .setExpire(expiration != null ? expiration.getTime() : 0L)
                .setTokenType(tokenType).setUserId(userId)
                .setUsername(username).setNickName(nickName);
    }

    /**
     * 解析刷新token
     *
     * @param token 待解析的token
     * @return 认证信息
     */
    public AuthInfo parseRefreshToken(String token) {
        Claims claims = JwtUtil.parseJwt(token, jwtProperties.getAllowedClockSkewSeconds());
        String tokenType = Convert.toStr(claims.get(JWT_KEY_TOKEN_TYPE));
        Long userId = Convert.toLong(claims.get(JWT_KEY_USER_ID));
        Date expiration = claims.getExpiration();
        return new AuthInfo().setToken(token)
                .setExpire(expiration != null ? expiration.getTime() : 0L)
                .setTokenType(tokenType).setUserId(userId);
    }
}
