package com.lotstock.eddid.common.core.util;

import com.alibaba.fastjson.JSONObject;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import com.nimbusds.openid.connect.sdk.UserInfoErrorResponse;
import com.nimbusds.openid.connect.sdk.UserInfoRequest;
import com.nimbusds.openid.connect.sdk.UserInfoResponse;
import com.nimbusds.openid.connect.sdk.UserInfoSuccessResponse;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Slf4j
public class OIDCUtil {

    public static JSONObject getUserInfo(String url, String tokenString) {
        try {
            /** 获取当前系统时间*/
            long startTime =  System.currentTimeMillis();

    /** 获取当前的系统时间，与初始时间相减就是程序运行的毫秒数，除以1000就是秒数*/
            long endTime =  System.currentTimeMillis();
            long usedTime = (endTime-startTime)/1000;
            URI userInfoEndpoint = new URI(url);    // The UserInfoEndpoint of the OpenID provider
            BearerAccessToken accessToken = new BearerAccessToken(tokenString); // The access token
            // Get OIDC user info
            HTTPResponse httpResponse = new UserInfoRequest(userInfoEndpoint, accessToken).toHTTPRequest().send();
            UserInfoResponse userinfoResponse = UserInfoResponse.parse(httpResponse);
            //
            if (!userinfoResponse.indicatesSuccess()) {
                UserInfoErrorResponse error = (UserInfoErrorResponse) userinfoResponse;
                log.info("FAILED error:  url is (" + url + ") , and token is (" + tokenString + ")");
                log.error("FAILED TO GET USER INFO : " + error.getErrorObject());
            } else {
                /**
                 * IF SUCCESS
                 */
                UserInfoSuccessResponse userinfoSuccessResponse = (UserInfoSuccessResponse) userinfoResponse;
                String userInfoString = userinfoSuccessResponse.getUserInfo().toJSONObject().toString();
                JSONObject userInfo = JSONObject.parseObject(userInfoString);
                return userInfo;
            }
        } catch (Exception e) {
            log.info("token parse url is (" + url + ") , and token is (" + tokenString + ")");
            log.error("access token parse error : " + e.toString());
        }
        return null;
    }
}
