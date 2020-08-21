package com.gome.oa.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gome.oa.common.ProjectNameVO;
import com.gome.oa.pojo.Project;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dmx
 * @since 2020-07-28
 */
public interface ProjectService extends IService<Project> {
    public List<Project> getProjectByUserId(Integer userId, Page<Project> page);

    public List<ProjectNameVO> getProjectName(Integer userId);
}
