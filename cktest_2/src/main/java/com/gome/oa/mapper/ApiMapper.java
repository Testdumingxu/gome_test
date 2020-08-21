package com.gome.oa.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gome.oa.common.ApiListVO;
import com.gome.oa.common.ApiVO;
import com.gome.oa.pojo.Api;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Data;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dmx
 * @since 2020-07-31
 */

public interface ApiMapper extends BaseMapper<Api> {

    // 查询分类下对应的接口
    @Select("SELECT * FROM api WHERE api_classification_id = #{apiClassificationId}")
    public List<Api> findApi(Integer apiClassificationId);

    /**
     * 查询当前用户下的所有接口
     * @return
     */
    @Select("SELECT t1.*,t2.name classificationName " +
            "FROM api t1, api_classification t2 " +
            "WHERE t1.api_classification_id = t2.id AND t1.create_user = #{userId}")
    public List<ApiListVO> findAllApi(Integer userId, Page<ApiListVO> page);

    /**
     * 根据接口id查询出接口信息及用户名
     * @param apiId 接口id
     * @return
     */
    @Select("SELECT a.*,u.username createUsername FROM api a, `user` u WHERE a.id = #{apiId} AND a.create_user = u.id")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "id", property = "requestParams",
                    many = @Many(select = "com.gome.oa.mapper.ApiRequestParamMapper.findAll"))
    })
    public List<ApiVO> findApiViewVO(Integer apiId);

    @Select("SELECT t1.*,t2.name classificationName " +
            "FROM api t1 JOIN api_classification t2 " +
            "ON t1.api_classification_id = t2.id " +
            "WHERE t1.name LIKE concat('%',#{apiName},'%') AND t1.create_user = #{userId}")
    public List<ApiListVO> findByName(String apiName, Long userId, Page<ApiListVO> page);
}
