package com.gome.oa.controller;


import com.gome.oa.common.ApiClassificationNameVo;
import com.gome.oa.common.ApiClassificationVO;
import com.gome.oa.common.Result;
import com.gome.oa.service.ApiClassificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
