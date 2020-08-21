package com.gome.oa.mapper;

import com.gome.oa.pojo.ApiRequestParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dmx
 * @since 2020-08-02
 */
public interface ApiRequestParamMapper extends BaseMapper<ApiRequestParam> {

    @Select("SELECT * FROM api_request_param WHERE api_id = #{apiId}")
    public List<ApiRequestParam> findAll(Integer apiId);

    @Select("SELECT DISTINCT t2.*,t1.api_request_param_value value, t1.id valueId " +
            "FROM case_param_value t1 " +
            "LEFT JOIN api_request_param t2 ON t1.api_request_param_id = t2.id " +
            "WHERE t1.case_id = #{caseId}")
    public List<ApiRequestParam> findByCase(Integer caseId);
}
