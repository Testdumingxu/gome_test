package com.gome.oa.mapper;

import com.gome.oa.common.ApiClassificationNameVo;
import com.gome.oa.common.ApiClassificationVO;
import com.gome.oa.pojo.ApiClassification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gome.oa.pojo.Project;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author dmx
 * @since 2020-07-31
 */
public interface ApiClassificationMapper extends BaseMapper<ApiClassification> {

    // 两表延迟加载 先查询分类信息（List<Api>）,按需加载（既此时查另一张表）
    @Select("SELECT * FROM api_classification WHERE project_id = #{projectId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "apis", many = @Many(select = "com.gome.oa.mapper.ApiMapper.findApi"))
    })
    public List<ApiClassificationVO> getWithApi(Integer projectId);

    /**
     * 返回当前用户下所有项目下的分类
     */
    @Select("SELECT cf.id classificationId,cf.name classificationName " +
            "FROM project p join api_classification cf " +
            "on cf.project_id = p.id AND p.create_user = #{userId}")
    public List<ApiClassificationNameVo> findProClassificationAll(Integer userId);
}
