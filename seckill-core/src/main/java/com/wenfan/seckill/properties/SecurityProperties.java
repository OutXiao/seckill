package com.wenfan.seckill.properties;

import com.wenfan.seckill.properties.Browser.BrowserProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by wenfan on 2020/1/26 20:37
 */
@ConfigurationProperties(prefix = "com.wenfan.security")
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }
}
