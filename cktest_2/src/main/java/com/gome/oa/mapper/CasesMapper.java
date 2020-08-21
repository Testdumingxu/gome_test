package com.gome.oa.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gome.oa.common.CaseEditVO;
import com.gome.oa.common.CaseListVO;
import com.gome.oa.pojo.Cases;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gome.oa.pojo.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dmx
 * @since 2020-08-11
 */
public interface CasesMapper extends BaseMapper<Cases> {

    /**
     * 查询所有测试用例
     * @return
     */
    @Select("SELECT DISTINCT c1.*, su1. NAME suiteName, a1.id apiId, a1.url apiUrl, a1. NAME apiName, a1.project_id projectId " +
            "FROM cases c1 LEFT JOIN suite su1 ON c1.suite_id = su1.id " +
            "LEFT JOIN project p1 ON su1.project_id = p1.id " +
            "LEFT JOIN case_param_value cpv1 ON cpv1.case_id = c1.id " +
            "LEFT JOIN api_request_param rep1 ON cpv1.api_request_param_id = rep1.id " +
            "LEFT JOIN api a1 ON rep1.api_id = a1.id " +
            "WHERE c1.create_user = #{userId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "testReports", column = "id", one = @One(select = "com.gome.oa.mapper.TestReportMapper.findByCaseId"))
    })
    public List<CaseListVO> showCaseUnderUserId(Integer userId, Page<CaseListVO> page);

    /**
     * 根据suiteId查询所有测试用例
     * @return
     */
    @Select("SELECT DISTINCT c1.*, su1. NAME suiteName, a1.id apiId, a1.url apiUrl, a1. NAME apiName, a1.project_id projectId " +
            "FROM cases c1 LEFT JOIN suite su1 ON c1.suite_id = su1.id " +
            "LEFT JOIN project p1 ON su1.project_id = p1.id " +
            "LEFT JOIN case_param_value cpv1 ON cpv1.case_id = c1.id " +
            "LEFT JOIN api_request_param rep1 ON cpv1.api_request_param_id = rep1.id " +
            "LEFT JOIN api a1 ON rep1.api_id = a1.id " +
            "WHERE c1.create_user = #{userId} AND c1.suite_id = #{suiteId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "testReports", column = "id", one = @One(select = "com.gome.oa.mapper.TestReportMapper.findByCaseId"))
    })
    public List<CaseListVO> showCaseUnderSuiteId(Integer userId, Integer suiteId, Page<CaseListVO> page);

    /**
     * 获取用例详情信息(基本case信息、参数信息)
     * @param caseId
     * @return
     */
    @Select("SELECT DISTINCT t1.*, t5.host, t4.id apiId, t4.url, t4.method " +
            "FROM cases t1 " +
            "LEFT JOIN case_param_value t2 ON t1.id = t2.case_id " +
            "LEFT JOIN api_request_param t3 ON t2.api_request_param_id = t3.id " +
            "LEFT JOIN api t4 ON t3.api_id = t4.id " +
            "LEFT JOIN project t5 ON t4.project_id = t5.id " +
            "WHERE t1.id = #{caseId} AND t1.create_user = #{userId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "requestParams", column = "id", many = @Many(select = "com.gome.oa.mapper.ApiRequestParamMapper.findByCase")),
            @Result(property = "testRules", column = "id", many = @Many(select = "com.gome.oa.mapper.TestRuleMapper.findByCase"))
    })
    public CaseEditVO findCaseEditVO(Integer caseId, Integer userId);
}
