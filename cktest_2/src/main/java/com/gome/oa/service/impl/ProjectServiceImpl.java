package com.gome.oa.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gome.oa.common.ProjectNameVO;
import com.gome.oa.pojo.Project;
import com.gome.oa.mapper.ProjectMapper;
import com.gome.oa.service.ProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dmx
 * @since 2020-07-28
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Autowired
    ProjectMapper projectMapper;

    @Override
    public List<Project> getProjectByUserId(Integer userId, Page<Project> page) {
        return projectMapper.getProjectByUserId(userId, page);
    }

    @Override
    public List<ProjectNameVO> getProjectName(Integer userId) {
        return projectMapper.getProjectName(userId);
    }
}
