package com.gome.oa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gome.oa.pojo.TestRule;
import com.gome.oa.mapper.TestRuleMapper;
import com.gome.oa.service.TestRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dmx
 * @since 2020-08-15
 */
@Service
public class TestRuleServiceImpl extends ServiceImpl<TestRuleMapper, TestRule> implements TestRuleService {

    @Autowired
    TestRuleService testRuleService;

    @Override
    public void delruleById(Integer ruleId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", ruleId);
        remove(queryWrapper);
    }
}
