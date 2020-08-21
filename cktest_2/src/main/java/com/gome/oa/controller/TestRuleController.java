package com.gome.oa.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gome.oa.common.Result;
import com.gome.oa.service.TestRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dmx
 * @since 2020-08-15
 */
@RestController
@RequestMapping("/testRule")
public class TestRuleController {

    @Autowired
    TestRuleService testRuleService;

    @DeleteMapping("/del")
    public Result delruleById(Integer ruleId) {

        testRuleService.delruleById(ruleId);
        return new Result("1", "删除断言成功");
    }
}
