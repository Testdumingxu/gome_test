package com.gome.oa.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gome.oa.pojo.User;
import com.gome.oa.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    // 身份认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 认证逻辑
        String username = authenticationToken.getPrincipal().toString();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User dbUser = userService.getOne(queryWrapper);
        if (dbUser != null) {
//            if (dbUser.getPassword().equals(authenticationToken.getCredentials())) { }
            return new SimpleAuthenticationInfo(dbUser, dbUser.getPassword(), getName());
        }
        return null;
    }

    // 授权（权限管理）
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
