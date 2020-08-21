package com.gome.oa.shiro;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

//@Configuration
public class ShiroConfig暂时不用 {
    /**
     * 过滤器配置
     *
     */
//    @Bean
//    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
//
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager((org.apache.shiro.mgt.SecurityManager) securityManager);
//        //拦截器
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
//        //配置不会被拦截的链接 顺序判断 相关静态资源
//        filterChainDefinitionMap.put("/user/login", "anon");
//        filterChainDefinitionMap.put("/user/find", "anon");
//        filterChainDefinitionMap.put("/user/register", "anon");
//        filterChainDefinitionMap.put("/druid/**", "anon");
//        //所有的url都必须认证通过才可以访问
//        filterChainDefinitionMap.put("/**", "authc");
//        //未授权界面
////        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        // 如果不设置默认会自动寻找Web工程根目录下的“/login”页面
//        shiroFilterFactoryBean.setLoginUrl("/user/unauth");
//        return shiroFilterFactoryBean;
//    }

    // 重新设置SecurityManager， 通过自动以的MyRealm完成登录校验
    @Bean
    public MyRealm myrealm() {
        return new MyRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager(MyRealm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义session管理
//        securityManager.setSessionManager(sessionManager());
        // 设置realm
        securityManager.setRealm(myrealm());
        return securityManager;
    }

}
