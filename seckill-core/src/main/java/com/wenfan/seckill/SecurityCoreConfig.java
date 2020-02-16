package com.wenfan.seckill;


import com.wenfan.seckill.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author:wenfan
 * @description:
 * @data: 2018/12/29 23:52
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {


}
