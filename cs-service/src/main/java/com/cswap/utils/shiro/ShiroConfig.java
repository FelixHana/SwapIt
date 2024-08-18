package com.cswap.utils.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

    /*
     * 倒序配置
     *   1、先自定义过滤器 MyRealm
     *   2、创建第二个DefaultWebSecurityManager，将MyRealm注入
     *   3、装配第三个ShiroFilterFactoryBean，将DefaultWebSecurityManager注入，并注入认证及授权规则
     * */

    //3、装配ShiroFilterFactoryBean，并将 DefaultWebSecurityManager 注入到 ShiroFilterFactoryBean 中
    @Bean
    public ShiroFilterFactoryBean factoryBean(DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(manager);//将 DefaultWebSecurityManager 注入到 ShiroFilterFactoryBean 中
        //注入认证及授权规则
        return factoryBean;
    }

    //2、创建DefaultWebSecurityManager ，并且将 MyRealm 注入到 DefaultWebSecurityManager bean 中
    @Bean
    public DefaultWebSecurityManager manager(MyRealm myRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myRealm);//将自定义的 MyRealm 注入到 DefaultWebSecurityManager bean 中
        return manager;
    }

    //1、自定义过滤器Realm
    @Bean
    public MyRealm myRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        MyRealm myRealm = new MyRealm();
        // 密码匹配器
        myRealm.setCredentialsMatcher(matcher);
        return myRealm;
    }

    /**
     * 密码匹配器
     *
     * @return HashedCredentialsMatcher
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 设置哈希算法名称
        matcher.setHashAlgorithmName("MD5");
        // 设置哈希迭代次数
        matcher.setHashIterations(1024);
        // 设置存储凭证(true:十六进制编码,false:base64)
        matcher.setStoredCredentialsHexEncoded(true);

        return matcher;
    }
}
