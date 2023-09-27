package com.jayen.internelcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jayen.internelcommon.dto.TokenResult;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    // 盐
    private static final String SIGN = "CPFJAYDEN!{>{}@";

    private static final String JWT_KEY_PHONE = "phone";

    // 0 乘客  1 司机
    private static final String JWT_KEY_IDENTITY = "identity";

    private static final String JWT_TOKEN_TYPE = "token_type";

    // 生成token
    public static String generatorToken(String passengerPhone, String identity, String tokenType) {
        Map<String, String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE, passengerPhone);
        map.put(JWT_KEY_IDENTITY, identity);
        map.put(JWT_TOKEN_TYPE, tokenType);
        // token 一天后过期
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();

        JWTCreator.Builder builder = JWT.create();
        // 整合map
        map.forEach(
                (k, v) -> {
                    builder.withClaim(k, v);
                }
        );
        // 整合过期时间
//        builder.withExpiresAt(date);
        // 生成token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));
        return sign;
    }

    // 解析token
    public static TokenResult parseToken(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(JWT_KEY_PHONE).asString();
        String identity = verify.getClaim(JWT_KEY_IDENTITY).asString();
        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);
        return tokenResult;
    }

    // 校验token，主要判断token时候异常
    public static TokenResult checkToken(String token){
        TokenResult tokenResult = null;
        try{
            tokenResult = JWTUtils.parseToken(token);
        }catch (Exception e){

        }
        return tokenResult;
    }

    public static void main(String[] args) {
        String s = generatorToken("17389889467","1","access");
        System.out.println("生成的token：" + s);
        System.out.println("解析token后的值：");
        TokenResult tokenResult = parseToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwaG9uZSI6IjE3Mzg5ODg5NDY3IiwiaWRlbnRpdHkiOiIxIiwiZXhwIjoxNjk1NTIzOTUyfQ.Bv8cnK8zvNi7bdSVkdRszNOadYWJlBHjQSje03c4R_g");
        System.out.println("手机号:"+tokenResult.getPhone());
        System.out.println("身份:"+tokenResult.getIdentity());
    }
}
