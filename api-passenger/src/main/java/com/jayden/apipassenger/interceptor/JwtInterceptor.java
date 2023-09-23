package com.jayden.apipassenger.interceptor;


import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.jayen.internelcommon.dto.ResponseResult;
import com.jayen.internelcommon.dto.TokenResult;
import com.jayen.internelcommon.util.JWTUtils;
import com.jayen.internelcommon.util.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;
        String resultString = "";
        String token = request.getHeader("Authorization");
        TokenResult tokenResult = null;
        try{
            tokenResult = JWTUtils.parseToken(token);
        }catch (SignatureVerificationException e){
            resultString = "token sign error";
            result = false;
        }catch (TokenExpiredException e){
            resultString = "token time out";
            result = false;
        }catch (AlgorithmMismatchException e){
            resultString = "token AlgorithmMismatchException";
            result = false;
        }catch (Exception e){
            resultString = "token invalid";
            result = false;
        }

        if (tokenResult == null){
            resultString = "token invalid";
            result = false;
        }else{
            // 拼接token
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();
            String tokenKey = RedisPrefixUtils.generatorTokenKey(phone,identity);
            // 从redis中取出token
            String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
            if (StringUtils.isBlank(tokenRedis)){
                resultString = "token invalid";
                result = false;
            }else{
                // 比较我们传入的token和redis中的token是否相等
                if (!token.trim().equals(tokenRedis.trim())){
                    resultString = "token invalid";
                    result = false;
                }
            }
        }


        if (!result){
            PrintWriter out = response.getWriter();
            out.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }

        return result;
    }
}
