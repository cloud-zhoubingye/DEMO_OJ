package com.example.admin_demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String KEY = "scuoj";

    //接受业务数据，生成token并返回
    public static String genToken(Map<String,Object> claims){
        //生成JWT的代码
        return JWT.create()
                .withClaim("claims",claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60))//添加过期时间,一小时
                .sign(Algorithm.HMAC256(KEY));//指定算法，配置密钥
    }

    //接受token，验证token，并返回业务数据
    public static Map<String,Object> parseToken(String token){
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }
}