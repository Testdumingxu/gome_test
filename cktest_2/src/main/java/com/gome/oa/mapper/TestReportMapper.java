package com.gome.oa.mapper;

import com.gome.oa.common.CaseEditVO;
import com.gome.oa.common.ReportVO;
import com.gome.oa.pojo.TestReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dmx
 * @since 2020-08-16
 */
public interface TestReportMapper extends BaseMapper<TestReport> {

    /**
     * 根据suiteId执行下面所有测试用例
     * @param userId
     * @param suiteId
     * @return
     */
    @Select("SELECT DISTINCT c1.*, su1.NAME suiteName, a1.id apiId, a1.url, a1.NAME apiName, a1.method, a1.project_id projectId, p1.host " +
            "FROM cases c1 " +
            "LEFT JOIN suite su1 ON c1.suite_id = su1.id " +
            "LEFT JOIN project p1 ON su1.project_id = p1.id " +
            "LEFT JOIN case_param_value cpv1 ON cpv1.case_id = c1.id " +
            "LEFT JOIN api_request_param rep1 ON cpv1.api_request_param_id = rep1.id " +
            "LEFT JOIN api a1 ON rep1.api_id = a1.id " +
            "WHERE c1.create_user = #{userId} AND c1.suite_id = #{suiteId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "requestParams", column = "id", many = @Many(select = "com.gome.oa.mapper.ApiRequestParamMapper.findByCase")),
            @Result(property = "testRules", column = "id", many = @Many(select = "com.gome.oa.mapper.TestRuleMapper.findByCase"))
    })
    List<CaseEditVO> findCaseEditVoUnderSuite(Integer userId, Integer suiteId);

    /**
     * 根据suiteId删除测试报告
     * @param suiteId
     */
    @Delete("DELETE FROM test_report WHERE case_id in(SELECT id FROM cases WHERE suite_id = #{suiteId})")
    void deleteReport(Integer suiteId);

    /**
     * 根据caseId获取报告
     * @param caseId
     * @return
     */
    @Select("select * from test_report where case_id = #{caseId}")
    TestReport findByCaseId(Integer caseId);

    /**
     * 获取某个套件下用例执行后的suiteid
     * @return
     */
    @Select("SELECT " +
            "t1.*,t2.suite_id suiteId " +
            "FROM " +
            "test_report t1 " +
            "JOIN cases t2 ON t1.case_id = t2.id " +
            "JOIN suite t3 ON t2.suite_id = t3.id")
    List<TestReport> getSuiteId();

    @Select("SELECT * FROM suite WHERE create_user = #{userId} AND id = #{suiteId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "createUser", column = "create_user"),
            @Result(property = "caseList", column = "{userId=create_user,suiteId=id}", many = @Many(select = "com.gome.oa.mapper.CasesMapper.showCaseUnderSuiteId"))
    })
    public ReportVO getReport(Integer userId, Integer suiteId);
}
