package com.gome.oa.mapper;

import com.gome.oa.pojo.TestRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dmx
 * @since 2020-08-15
 */
public interface TestRuleMapper extends BaseMapper<TestRule> {

    @Select("SELECT * FROM test_rule WHERE case_id = #{caseId}")
    public List<TestRule> findByCase(Integer caseId);
}
