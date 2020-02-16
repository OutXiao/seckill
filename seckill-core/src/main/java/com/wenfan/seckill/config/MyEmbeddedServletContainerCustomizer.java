package com.wenfan.seckill.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by wenfan on 2020/2/2 21:56
 *
 *
 * 定制内置Tomcat
 *
 */

@Component
public class MyEmbeddedServletContainerCustomizer implements EmbeddedServletContainerCustomizer {


    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        TomcatEmbeddedServletContainerFactory factory = (TomcatEmbeddedServletContainerFactory) container;
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                Http11NioProtocol http11NioProtocol = (Http11NioProtocol) connector.getProtocolHandler();
                // 定制化keepalivetimeout ，设置30秒内没有请求则断开连接
                http11NioProtocol.setKeepAliveTimeout(30000);
                // 客户端发送超过10000个请求则自动断开连接
                http11NioProtocol.setMaxKeepAliveRequests(10000);
            }
        });
    }
}
