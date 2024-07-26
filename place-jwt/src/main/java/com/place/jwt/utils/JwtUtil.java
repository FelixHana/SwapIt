package com.place.jwt.utils;






import com.place.common.domain.enums.CommonCodeEnum;
import com.place.common.exception.CommonException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import org.springframework.security.authentication.AuthenticationServiceException;

import javax.crypto.SecretKey;
import java.security.SignatureException;
import java.util.Date;
import java.util.UUID;

/**
 * @author ZCY-
 */
public class JwtUtil {

    // 签名

    private final static SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;
    private final static String SECRET = "secretKey!secretKey!secretKey!secretKey!";
    public static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    private final static String JWT_ISS = "zcy";
    // 创建Token
    public static String createJwt(Long userId){
        JwtBuilder jwtBuilder = Jwts.builder();//构建JWT对象
        // 时间毫秒数，token有效期（毫秒为单位）
        long time = 1000 * 60 * 60 * 24;
        return jwtBuilder
                // Header
                .header()
                .add("typ","JWT")
                .add("alg","HS256")
                .and()
                .subject("user")
                // 用户ID
                .claim("userId", userId)
                // 设置有效期
                .expiration(new Date(System.currentTimeMillis()+ time))
                // jti声明 唯一ID
                .id(UUID.randomUUID().toString())
                // 签发者
                .issuer(JWT_ISS)
                // signature
                .signWith(KEY, ALGORITHM)
                // compact拼接三部分header、payload、signature
                .compact();
    }

    public static Long parseUserId(String token) {
        return Long.valueOf(parseClaim(token).getPayload().get("userId").toString());
    }

    // 验证token是否有效
    public static Jws<Claims> parseClaim(String token){
        try {
            return Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new CommonException(CommonCodeEnum.AUTH_ERROR,e.getMessage());
        }
    }

}
