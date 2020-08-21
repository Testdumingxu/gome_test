package com.gome.oa.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gome.oa.common.ApiVO;
import com.gome.oa.common.CaseEditVO;
import com.gome.oa.common.CaseListVO;
import com.gome.oa.common.Result;
import com.gome.oa.mapper.CasesMapper;
import com.gome.oa.pojo.*;
import com.gome.oa.service.CaseParamValueService;
import com.gome.oa.service.CasesService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dmx
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/cases")
public class CasesController {

    @Autowired
    CasesService casesService;

    @Autowired
    CasesMapper casesMapper;

    @PostMapping("/add")
    public Result add( Cases caseVo,@RequestBody ApiVO apiRunVO) { // Cases caseVo, ApiVO apiRunVO

        // 添加到cases
        casesService.add(caseVo, apiRunVO);

        return new Result("1", "添加测试集成功");
    }

//    @GetMapping("/findAll")
//    public Result findCasesAll(Integer pageNo, Integer page_size) {
//
//        User user = (User) SecurityUtils.getSubject().getPrincipal();
//        // 使用QueryWrapper进行分页操作
//        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.eq("create_user", user.getId());
//
//        Page<Cases> page = new Page<>(pageNo == null ? 1 : pageNo, page_size == null ? 5 : page_size);
//        IPage data = casesMapper.selectPage(page, queryWrapper);
//
//        return new Result("1", data);
//    }

    @GetMapping("/findAll")
    public Result showCaseUnderUserId(Integer pageNo, Integer page_size) {
        Result result = null;
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        int userId = user.getId().intValue();
        Page<CaseListVO> page = new Page<>(pageNo == null ? 1 : pageNo,page_size == null ? 5 : page_size);
        IPage<CaseListVO> List = page.setRecords(casesMapper.showCaseUnderUserId(userId,page));
        result = new Result("1", List, "获取用例成功");
        return result;
    }

    @GetMapping("/showCase")
    public Result showCaseUnderSuiteId(Integer suiteId, Integer pageNo, Integer page_size) {
        Result result = null;
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        int userId = user.getId().intValue();
        Page<CaseListVO> page = new Page<>(pageNo == null ? 1 : pageNo,page_size == null ? 5 : page_size);
        IPage<CaseListVO> List = page.setRecords(casesMapper.showCaseUnderSuiteId(userId, suiteId, page));
        result = new Result("1", List, "获取用例成功");
        return result;
    }

    @GetMapping("/toCaseEdit")
    public Result showCaseUnderUserId(Integer caseId) {
        Result result = null;
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        int userId = user.getId().intValue();
        CaseEditVO data = casesService.findCaseEditVO(caseId, userId);
        result = new Result("1", data, "获取用例成功");
        return result;
    }

    @PutMapping("/update")
    public Result updateCase(@RequestBody CaseEditVO caseEditVO) {
        casesService.updateCase(caseEditVO);
        return new Result("1","更新用例成功");
    }
}
