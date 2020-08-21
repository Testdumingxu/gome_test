package com.gome.oa.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gome.oa.common.Result;
import com.gome.oa.common.SuiteListVO;
import com.gome.oa.pojo.Suite;
import com.gome.oa.pojo.User;
import com.gome.oa.service.SuiteService;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dmx
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/suite")
public class SuiteController {

    @Autowired
    SuiteService suiteService;

    @GetMapping("/listAll")
    public Result findSuiteAll(Integer pageNo, Integer page_size) {

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        int userId = user.getId().intValue();
        Page<SuiteListVO> page = new Page<>(pageNo == null ? 1 : pageNo,page_size == null ? 5 : page_size);
        List<SuiteListVO> suiteAll = suiteService.findSuiteAll(userId, page);
        Page<SuiteListVO> data = page.setRecords(suiteAll);

        return new Result("1", data);
    }

    @GetMapping("/update")
    public Result updateStatusBySuiteId(Integer suiteId) {
        suiteService.updateStatusBySuiteId(suiteId);
        return new Result("1", "更新状态成功");
    }
}
