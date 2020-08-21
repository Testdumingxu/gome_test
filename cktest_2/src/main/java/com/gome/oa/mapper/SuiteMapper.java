package com.gome.oa.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gome.oa.common.SuiteListVO;
import com.gome.oa.pojo.Suite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface SuiteMapper extends BaseMapper<Suite> {

    @Select("SELECT * FROM suite WHERE create_user = ${userId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "Project", column = "project_id", one = @One(select = "com.gome.oa.mapper.ProjectMapper.getProjectNameAll"))
    })
    public List<SuiteListVO> findSuiteAll(Integer userId, Page<SuiteListVO> page);

    @Update("UPDATE suite SET status = 1 WHERE id = #{suiteId}")
    public void updateStatusBySuiteId(Integer suiteId);
}
