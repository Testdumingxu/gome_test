package com.gome.oa.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * 自定义session获取方式
 * 采用ajax请求头authToken携带sessionId的方式
 **/
public class CustomSessionManager extends DefaultWebSessionManager {

    private static final String AUTHORIZATION = "Authorization";

    private static final String REFERENCED_SESSION_ID_SOURCE = "cookie";

    public CustomSessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        // 获取前端的请求头AUTHORIZATION
        String sessionId = WebUtils.toHttp(request).getHeader(AUTHORIZATION);

        // 判断session是否不为空
         if (StringUtils.isNotEmpty(sessionId)) {
             //如果请求头中有 Authorization 则其值为sessionId
             request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
             request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
             request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sessionId;
        } else {
             return super.getSessionId(request, response);
        }
    }
}