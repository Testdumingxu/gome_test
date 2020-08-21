package com.gome.oa.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gome.oa.common.ProjectNameVO;
import com.gome.oa.pojo.Project;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dmx
 * @since 2020-07-28
 */
public interface ProjectMapper extends BaseMapper<Project> {

    /**
     * 根据当前用户查询项目
     * @param userId
     * @return
     */
    @Select("SELECT * FROM project WHERE create_user = #{userId}")
    public List<Project> getProjectByUserId(Integer userId, Page<Project> page);

    @Select("SELECT id, name FROM project WHERE create_user = #{userId}")
    public List<ProjectNameVO> getProjectName(Integer userId);

    @Select("SELECT id,name FROM project WHERE id = #{projectId}")
    public List<Project> getProjectNameAll(Integer projectId);
}
