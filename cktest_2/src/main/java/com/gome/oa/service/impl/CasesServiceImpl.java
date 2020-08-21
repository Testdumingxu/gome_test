package com.gome.oa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gome.oa.common.ApiVO;
import com.gome.oa.common.CaseEditVO;
import com.gome.oa.common.CaseListVO;
import com.gome.oa.common.Result;
import com.gome.oa.pojo.ApiRequestParam;
import com.gome.oa.pojo.CaseParamValue;
import com.gome.oa.pojo.Cases;
import com.gome.oa.mapper.CasesMapper;
import com.gome.oa.pojo.TestRule;
import com.gome.oa.service.CaseParamValueService;
import com.gome.oa.service.CasesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gome.oa.service.TestRuleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dmx
 * @since 2020-08-11
 */
@Service
public class CasesServiceImpl extends ServiceImpl<CasesMapper, Cases> implements CasesService {

    @Autowired
    CaseParamValueService caseParamValueService;

    @Autowired
    TestRuleService testRuleService;

    @Autowired
    CasesMapper casesMapper;

    // 添加用例
    public void add(Cases caseVo, ApiVO apiRunVO) {
        // 添加到 cases
        this.save(caseVo);
        // 批量添加到case_param_value
        List<ApiRequestParam> requestParams = apiRunVO.getRequestParams();

        ArrayList<CaseParamValue> caseParamValues = new ArrayList<>();
        for (ApiRequestParam apiRequestParam : requestParams) {
            CaseParamValue caseParamValue = new CaseParamValue();
            caseParamValue.setCaseId(caseVo.getId());
            caseParamValue.setCreateUser(caseVo.getCreateUser());
            caseParamValue.setApiRequestParamId(apiRequestParam.getId());
            caseParamValue.setApiRequestParamValue(apiRequestParam.getValue());
            caseParamValues.add(caseParamValue);
        }
        caseParamValueService.saveBatch(caseParamValues);
    }

    @Override
    public List<CaseListVO> showCaseUnderUserId(Integer userId, Page<CaseListVO> page) {
        return casesMapper.showCaseUnderUserId(userId, page);
    }

    @Override
    public CaseEditVO findCaseEditVO(Integer caseId, Integer userId) {
        return casesMapper.findCaseEditVO(caseId, userId);
    }

    @Override
    public List<CaseListVO> showCaseUnderSuiteId(Integer userId, Integer suiteId, Page<CaseListVO> page) {
        return casesMapper.showCaseUnderSuiteId(userId, suiteId, page);
    }

    @Override
    public void updateCase(CaseEditVO caseEditVO) {
        // 此时只能更新case表和case_param_value表还需要更新test_rule表
        updateById(caseEditVO);

        // 更新case_param_value表
        List<ApiRequestParam> requestParams = caseEditVO.getRequestParams();
        List<CaseParamValue> caseParamValues = new ArrayList<>();
        for (ApiRequestParam apiRequestParam: requestParams) {
            CaseParamValue caseParamValue = new CaseParamValue();
            caseParamValue.setId(apiRequestParam.getValueId().longValue());
            caseParamValue.setCaseId(caseEditVO.getId());
            caseParamValue.setApiRequestParamId(apiRequestParam.getId());
            caseParamValue.setApiRequestParamValue(apiRequestParam.getValue());
            caseParamValue.setCreateUser(caseEditVO.getCreateUser());

            caseParamValues.add(caseParamValue);
        }
        caseParamValueService.updateBatchById(caseParamValues);
        // 更新test_rule表
        // 数组里面为空值的不插入
        List<TestRule> testRules = new ArrayList<>();
        for (TestRule testRule : caseEditVO.getTestRules()) {
            if (StringUtils.isNotBlank(testRule.getExpression()) && StringUtils.isNotBlank(testRule.getExpected()) && StringUtils.isNotBlank(testRule.getOperator())) {
                testRules.add(testRule);
            }
        }
        // 更新或插入值
        testRuleService.saveOrUpdateBatch(testRules);

    }
}
