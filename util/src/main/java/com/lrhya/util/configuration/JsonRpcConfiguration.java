package com.lrhya.util.configuration;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcClientProxyCreator;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
//import com.lrhya.api.ProductRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
//@ComponentScan(basePackageClasses = {ProductRpc.class})
public class JsonRpcConfiguration {

    private static Logger LOG = LoggerFactory.getLogger(JsonRpcConfiguration.class);

    @Bean
    public AutoJsonRpcServiceImplExporter rpcServiceImplExporter() {

        return new AutoJsonRpcServiceImplExporter();
    }



//    @Bean
////    @ConditionalOnProperty(value = {"rpc.client.url","rpc.client.basePackage"})
//    public  static AutoJsonRpcClientProxyCreator rpcClientProxyCreator(@Value("${rpc.manager.url}") String url) {
//        AutoJsonRpcClientProxyCreator creator = new AutoJsonRpcClientProxyCreator();
//        try {
//            creator.setBaseUrl(new URL(url));
//        } catch (MalformedURLException e) {
//            LOG.error("创建rpc服务地址错误", e);
//        }
//        creator.setScanPackage(ProductRpc.class.getPackage().getName());
//        return creator;
//    }
    @Bean
    @ConditionalOnProperty(value = {"rpc.client.url","rpc.client.basePackage"}) //当配置文件中有这两个属性时才需要导出客户端
    public AutoJsonRpcClientProxyCreator rpcClientProxyCreator(@Value("${rpc.client.url}") String url,
                                                               @Value("${rpc.client.basePackage}") String basePackage){
        AutoJsonRpcClientProxyCreator clientProxyCreator = new AutoJsonRpcClientProxyCreator();
        try {
            //配置基础url
            clientProxyCreator.setBaseUrl(new URL(url));
        } catch (MalformedURLException e) {
            LOG.error("创建rpc服务地址错误");
        }
        //让它扫描api下rpc服务的包
        clientProxyCreator.setScanPackage(basePackage);
        return clientProxyCreator;
    }
}
