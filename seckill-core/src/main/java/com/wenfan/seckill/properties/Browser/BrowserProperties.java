package com.wenfan.seckill.properties.Browser;

/**
 * Created by wenfan on 2020/1/26 20:40
 */
public class BrowserProperties {

    // 不被拦截的url
    private String unAuthorizationUrl = "";

    public String getUnAuthorizationUrl() {
        return unAuthorizationUrl;
    }

    public void setUnAuthorizationUrl(String unAuthorizationUrl) {
        this.unAuthorizationUrl = unAuthorizationUrl;
    }
}
