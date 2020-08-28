package com.gome.oa.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gome.oa.common.ApiClassificationNameVo;
import com.gome.oa.common.ApiClassificationVO;
import com.gome.oa.common.ClassificationVO;
import com.gome.oa.common.Result;
import com.gome.oa.pojo.ApiClassification;
import com.gome.oa.pojo.Project;
import com.gome.oa.pojo.User;
import com.gome.oa.service.ApiClassificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dmx
 * @since 2020-07-31
 */
@RestController
@RequestMapping("/apiClassification")
@Api("接口分类模块")
public class ApiClassificationController {

    @Autowired
    ApiClassificationService apiClassificationService;

    @GetMapping("/get")
    public Result findAll(Integer userId, Integer pageNo, Integer page_size) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Page<ClassificationVO> page = new Page<>(pageNo == null ? 1 : pageNo,page_size == null ? 5 : page_size);
        IPage<ClassificationVO> List = page.setRecords(apiClassificationService.findAll(user.getId().intValue(),page));
        return new Result("1", List, "查询接口分类成功");
    }

    @PostMapping("/add")
    public Result ClassificationAdd(@RequestBody ApiClassification apiClassification) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        apiClassification.setCreateUser(user.getId());

        apiClassificationService.save(apiClassification);
        return new Result("1", "新增分类成功");
    }

    @GetMapping("/toIndex")
    @ApiOperation(value = "获取所有分类信息", httpMethod = "GET")
    public Result getWithApi(Integer projectId, Integer tab) {
        Result result = null;
        if (tab == 1) {
            // 接口列表
            List<ApiClassificationVO> list = apiClassificationService.getWithApi(projectId);
            result = new Result("1", list, "查询接口分类延迟加载api");
        } else {
            // 测试集合
        }
        return result;
    }

    @GetMapping("/toName")
    @ApiOperation(value = "获取项目下所有分类信息", httpMethod = "GET")
    public Result findProClassificationAll(Integer userId) {
        Result result = null;

        List<ApiClassificationNameVo> list = apiClassificationService.findProClassificationAll(userId);
        result = new Result("1", list, "返回项目下分类信息");
        return result;
    }
}
