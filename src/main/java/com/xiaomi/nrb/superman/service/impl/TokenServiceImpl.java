package com.xiaomi.nrb.superman.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xiaomi.nrb.superman.domain.User;
import com.xiaomi.nrb.superman.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    private static final String SECRET = "QW897alpmnQQ";

    //创建token
    public String createToken(User user) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("alg", "HS256");
            map.put("typ", "JWT");
            //过期时间
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 2);
            String token = JWT.create()
                    .withHeader(map)
                    .withClaim("openId", user.getOpenId())
                    .withClaim("id", user.getId() + "")
                    .withExpiresAt(calendar.getTime())
                    .sign(Algorithm.HMAC256(SECRET));

            return token;
        } catch (Exception e) {
            return null;
        }
    }

    //验证token
    public boolean validateToken(String token) {
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token).getToken();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //获取某参数
    public String getParam(String token, String key) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null) return null;
            return decodedJWT.getClaim(key).asString();
        } catch (Exception e) {
            return null;
        }
    }

}
