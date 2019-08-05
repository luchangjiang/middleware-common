package com.lotstock.eddid.common.param;

import com.lotstock.eddid.common.core.exception.BaseResultEnum;
import com.lotstock.eddid.common.core.exception.ResultException;
import com.lotstock.eddid.common.core.util.CryptoAESUtils;
import com.lotstock.eddid.common.core.util.JSRSAUtils;
import com.lotstock.eddid.common.core.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.util.UUID;

@Slf4j
public class ParamManager {
    private static final Long EXPIRE_TIME = 24 * 60L * 60L;
    private static final String TOKEN_NAME = "token";
    private static final String CACHE_KEY_PATTERN = "u_%s";

    private RSAPrivateKey privateKey;
    private StringRedisTemplate stringRedisTemplate;

    private static ParamManager instance;

    public static synchronized ParamManager getInstance(StringRedisTemplate stringRedisTemplate, String privateKey) {
        if (instance == null) {
            instance = new ParamManager(stringRedisTemplate, privateKey);
        }
        return instance;
    }

    private ParamManager(StringRedisTemplate stringRedisTemplate, String privateKey) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.privateKey = (RSAPrivateKey) JSRSAUtils.generatePrivateKey(privateKey);
    }

    private String decodeParamsKey(String paramKey) throws IOException {
        System.out.println(privateKey);
        return JSRSAUtils.decryptBase64(paramKey, privateKey);
    }

    /**
     * 保存参数加密KEY，并返回会话ID到Cookie和Header
     *
     * @param request
     * @param response
     * @param paramKeyString
     * @return
     */
    public String saveParamKey(HttpServletRequest request, HttpServletResponse response, String paramKeyString) throws IOException {
        System.out.println(paramKeyString);
//        System.out.println(URLDecoder.decode(paramKeyString, "UTF-8"));
        String paramKey = decodeParamsKey(paramKeyString);
        System.out.println(paramKey);
        String token = getToken(request);
        if ("".equals(token) || paramKey == null) {
            return null;
        }

        // 将会话ID保存到Redis中
        stringRedisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(getTokenCacheKey(token), EXPIRE_TIME, paramKey.getBytes());
                return null;
            }
        });

        // 添加返回
        WebUtils.setCookie(response, TOKEN_NAME, token, 3600);
        response.setHeader(TOKEN_NAME, token);
        return token;
    }


    /**
     * 获取、生成会话ID
     *
     * @param request
     * @return
     */
    private String getToken(HttpServletRequest request, boolean isCreate) {
        String token = request.getHeader(TOKEN_NAME);
        if (token == null) {
            token = WebUtils.getCookieVal(request, TOKEN_NAME);
        }
        if (isCreate && (token == null || "".equals(token) || "null".equals(token))) {
            token = UUID.randomUUID().toString().replace("-", "");
        }
        return token;
    }

    private String getToken(HttpServletRequest request) {
        return getToken(request, true);
    }

    public byte[] getTokenCacheKey(String token) {
        return String.format(CACHE_KEY_PATTERN, token).getBytes();
    }


    public String decodeParamString(String token, String paramString) throws IOException {
        if (StringUtils.isEmpty(paramString)) {
            return paramString;
        }
        if (token == null || "".equals(token.trim())) {
            throw new ResultException(BaseResultEnum.PARAM_TOKEN_NULL.getResultModel());
        }
        byte[] paramKey = stringRedisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = getTokenCacheKey(token);
                byte[] val = connection.get(key);
                if (val != null) {
                    connection.expire(key, EXPIRE_TIME);
                }
                return val;
            }
        });
        if (paramKey == null) {
            throw new ResultException(BaseResultEnum.PARAM_TOKEN_EXPIRE.getResultModel());
        }

        String paramKeyString = new String(paramKey);
        int keyLen = paramKeyString.length();
        String strKey;
        String ivKey;
        if (keyLen == 0) {
            throw new ResultException(BaseResultEnum.PARAM_TOKEN_EXPIRE.getResultModel());
        } else if (keyLen <= 16) {
            strKey = paramKeyString.substring(0, keyLen);
            ivKey = null;
        } else {
            strKey = paramKeyString.substring(0, 16);
            ivKey = paramKeyString.substring(16, keyLen);
        }
        try {
            return CryptoAESUtils.decryptString(strKey, ivKey, "AES/CBC/NoPadding", paramString);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(e, BaseResultEnum.PARAM_TOKEN_FAIL.getResultModel());
        }
    }

    /**
     * 加密字符串
     *
     * @param token
     * @param paramString
     * @return
     * @throws Exception
     */
    public String encryptString(String token, String paramString) throws Exception {
        if (StringUtils.isEmpty(paramString)) {
            return paramString;
        }
        if (token == null || "".equals(token.trim())) {
            throw new ResultException(BaseResultEnum.PARAM_TOKEN_NULL.getResultModel());
        }
        byte[] paramKey = stringRedisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = getTokenCacheKey(token);
                byte[] val = connection.get(key);
                if (val != null) {
                    connection.expire(key, EXPIRE_TIME);
                }
                return val;
            }
        });
        if (paramKey == null) {
            throw new ResultException(BaseResultEnum.PARAM_TOKEN_EXPIRE.getResultModel());
        }

        String paramKeyString = new String(paramKey);
        int keyLen = paramKeyString.length();
        String strKey;
        String ivKey;
        if (keyLen == 0) {
            throw new ResultException(BaseResultEnum.PARAM_TOKEN_EXPIRE.getResultModel());
        } else if (keyLen <= 16) {
            strKey = paramKeyString.substring(0, keyLen);
            ivKey = null;
        } else {
            strKey = paramKeyString.substring(0, 16);
            ivKey = paramKeyString.substring(16, keyLen);
        }
        try {
            return CryptoAESUtils.encryptAsBase64String(strKey, ivKey, "AES/CBC/NoPadding", paramString);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResultException(e, BaseResultEnum.PARAM_TOKEN_FAIL.getResultModel());
        }
    }
}
