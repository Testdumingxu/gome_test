package com.gome.oa.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.gome.oa.common.Result;
import com.gome.oa.pojo.User;
import com.gome.oa.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dmx
 * @since 2020-07-19
 */
@RestController
//@CrossOrigin // 支持跨域
@RequestMapping("/user")
@Api("用户模块")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping(value = "/register")
    @ApiOperation(value = "注册方法", httpMethod = "POST")
    public Result register(@RequestBody User user) {
        // 调用业务层, 插入db，统一异常处理
        userService.save(user);
        Result result = new Result("1", "注册成功");
        return result;
    }

    /**
     * 账号验重
     * @param username
     * @return
     */
    @GetMapping(value = "/find")
    @ApiOperation(value = "账号验重方法", httpMethod = "GET")
    public Result find(String username) {
        Result result = null;
        // 调用业务层方法，查询db非主键列
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        User user = userService.getOne(queryWrapper);
        if (user==null) {
            result = new Result("1", "账号不存在");
        } else {
            result = new Result("0", "账号已存在");
        }
        return result;
    }

    /**
     * 登录方法
     * @param user
     * @return
     */
    @PostMapping(value = "/login")
    @ApiOperation(value = "登录方法", httpMethod = "POST")
    public Result login(@RequestBody User user) {
        Result result = null;
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            // shiro 获取当前操作对象
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            // 将sessionId返回去
            String sessionId = (String) SecurityUtils.getSubject().getSession().getId();
            User loginUser = (User) subject.getPrincipal();
            loginUser.setPassword(null); // 将密码清空返回给前端
            result = new Result("1",loginUser,sessionId);
        }catch (AuthenticationException e) {
            if (e instanceof UnknownAccountException ) {
                result = new Result("0", "用户名错误");
            } else {
                result = new Result("0", "密码错误");
            }
            e.printStackTrace();
        }
        // 调用业务层方法，查询db，根据username是否为空
        return result;
    }

    /**
     * 退出方法
     * @return
     */
    @GetMapping("/logout")
    @ApiOperation(value = "退出方法", httpMethod = "GET")
    public Result logout() {
        Result result = null;
        // 从shiro退出会话
        SecurityUtils.getSubject().logout();
        result = new Result("1", "账号未登录");
        return result;
    }
}
