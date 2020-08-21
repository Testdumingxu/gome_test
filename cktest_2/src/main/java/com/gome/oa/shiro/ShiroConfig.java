package com.gome.oa.shiro;


import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * Filter工厂
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //配置不会被拦截的链接 顺序判断 相关静态资源
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/user/find", "anon");
        filterChainDefinitionMap.put("/user/register", "anon");
        filterChainDefinitionMap.put("/user/logout", "anon");
//        filterChainDefinitionMap.put("/project/toList", "anon");

        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        //所有的url都必须认证通过才可以访问
        filterChainDefinitionMap.put("/**", "authc");
        //未授权界面
        //  shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        // 如果不设置默认会自动寻找Web工程根目录下的“/login”页面
        shiroFilterFactoryBean.setLoginUrl("/user/unauth");
        return shiroFilterFactoryBean;
    }


    /**
     * 自定义Realm
     */
    // 重新设置SecurityManager， 通过自定义的MyRealm完成登录校验
    @Bean
    public MyRealm myrealm() {
        return new MyRealm();
    }

    /**
     * 安全管家
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("myrealm") MyRealm myrealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义session管理
        securityManager.setSessionManager(sessionManager());
        //关联realm
        securityManager.setRealm(myrealm);
        return securityManager;
    }

    @Bean
    public SessionManager sessionManager() {
        CustomSessionManager manager = new CustomSessionManager();
        manager.setSessionDAO(new EnterpriseCacheSessionDAO());
        return manager;
    }
}
