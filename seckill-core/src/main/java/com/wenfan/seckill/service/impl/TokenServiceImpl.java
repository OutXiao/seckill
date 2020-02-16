package com.wenfan.seckill.service.impl;

import com.wenfan.seckill.dto.Token;
import com.wenfan.seckill.exception.SystemException;
import com.wenfan.seckill.rest.RestMsg;
import com.wenfan.seckill.service.TokenService;
import com.wenfan.seckill.vo.LoginUser;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by wenfan on 2019/12/28 14:23
 */
@Service
public class TokenServiceImpl implements TokenService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Value("${token.expire.seconds}")
    private Integer expireSeconds;
    @Value("${token.expire.jwtSecret}")
    private String jwtSecret;

    private static Key KEY = null;
    private static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

    @Autowired
    private RedisTemplate<String, LoginUser> redisTemplate;

    @Override
    public Token saveToken(LoginUser user) {
        /*user.setToken(UUID.randomUUID().toString());*/
        cacheLoginUser(user);
        log.info("{} 已登录", user.getUsername());
        String jwtToken = createJWTToken(user);
        return new Token(jwtToken, user.getLoginTime());
    }

    @Override
    public void freshToken(LoginUser user) {
        cacheLoginUser(user);
    }

    @Override
    public LoginUser getLoginUser(String token) {
        String username = getUsernameFromJWT(token);
        if (username != null) {
            LoginUser loginUser = redisTemplate.boundValueOps(getTokenKey(username)).get();
                if (loginUser == null)
                    throw new SystemException(RestMsg.LOGIN_TOKEN_NOT_EXIST);
            return loginUser;
        }
        return null;
    }

    @Override
    public boolean deleteToken(String token) {
        String username = getUsernameFromJWT(token);
        if (username != null) {
            String key = getTokenKey(username);
            LoginUser loginUser = redisTemplate.opsForValue().get(key);
            if (loginUser != null) {
                redisTemplate.delete(key);
                // 退出日志
                //logService.save(loginUser.getId(), "退出", true, null);
                log.info("{} 已退出", loginUser.getUsername());
                return true;
            }
        }

        return false;
    }


    /**
     * 生成jwt
     *
     * @param loginUser
     * @return
     */
    private String createJWTToken(LoginUser loginUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(LOGIN_USER_KEY, loginUser.getUsername());// 放入一个随机字符串，通过该串可找到登陆用户

        String jwtToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance())
                .compact();

        return jwtToken;
    }


    private void cacheLoginUser(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireSeconds * 1000);
        // 根据uuid将loginUser缓存
        /*String token = loginUser.getToken();*/
        String tokenKey = getTokenKey(loginUser.getUsername());

        redisTemplate.boundValueOps(tokenKey).set(loginUser, expireSeconds, TimeUnit.SECONDS);
    }


    private String getTokenKey(String username) {
        return "username:" + username;
    }

    private Key getKeyInstance() {
        if (KEY == null) {
            synchronized (TokenServiceImpl.class) {
                if (KEY == null) {// 双重锁
                    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
                    KEY = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
                }
            }
        }

        return KEY;
    }

    private String getUsernameFromJWT(String jwtToken) {
        if ("null".equals(jwtToken) || StringUtils.isBlank(jwtToken)) {
            return null;
        }

        try {
            Map<String, Object> jwtClaims = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwtToken).getBody();
            return MapUtils.getString(jwtClaims, LOGIN_USER_KEY);
        } catch (ExpiredJwtException e) {
            log.error("{}已过期", jwtToken);
        } catch (Exception e) {
            log.error("{}", e);
        }

        return null;
    }
}

