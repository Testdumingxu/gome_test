package com.gome.oa.service;

import com.gome.oa.pojo.TestRule;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dmx
 * @since 2020-08-15
 */
public interface TestRuleService extends IService<TestRule> {

    public void delruleById(Integer ruleId);
}
