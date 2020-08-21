package com.gome.oa.controller;


import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gome.oa.common.ProjectNameVO;
import com.gome.oa.common.Result;
import com.gome.oa.mapper.ProjectMapper;
import com.gome.oa.pojo.Project;
import com.gome.oa.pojo.User;
import com.gome.oa.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dmx
 * @since 2020-07-28
 */
@RestController
@RequestMapping("/project")
@Api("项目模块")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    /**
     * 根据userid查询出下面的所有项目
     * @param userId
     * @param pageNo 当前页
     * @param page_size 每页显示多少条
     * @return
     */
    @GetMapping("/toList")
    @ApiOperation(value = "查询项目列表方法", httpMethod = "GET")
    public Result tolist(Integer userId,Integer pageNo, Integer page_size) {
        Result result = null;
        Page<Project> page = new Page<>(pageNo == null ? 1 : pageNo,page_size == null ? 5 : page_size);
        IPage<Project> List = page.setRecords(projectService.getProjectByUserId(userId,page));
        result = new Result("1", List, "项目列表");
        return result;
    }

    /**
     * 根据userid查询出下面的所有项目
     * @param userId
     * @return
     */
//    @GetMapping("/List")
//    @ApiOperation(value = "查询项目列表方法", httpMethod = "GET")
//    public Result tolist(Integer userId) {
//        Result result = null;
//        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.eq("create_user", userId);
//        List list = projectService.list(queryWrapper);
//        result = new Result("1", list, "项目列表");
//        return result;
//    }

    /**
     * 添加项目
     * @param project
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增项目方法", httpMethod = "POST")
    public Result add(@RequestBody Project project) {
        Result result = null;

        // 获取userid
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        project.setCreateUser(user.getId());
        // 新增项目数据
        projectService.save(project);

        result = new Result("1","新增项目成功");
        return result;
    }

    /**
     * 根据projectId获取项目详情
     * @param projectId
     * @return
     */
    @GetMapping("/{projectId}")
    @ApiOperation(value = "项目详情方法", httpMethod = "GET")
    public Result getById(@PathVariable("projectId") Integer projectId) {
        Result result = null;

        Project project = projectService.getById(projectId);
        result = new Result("1",project,"项目详情");
        return result;
    }

    /**
     * 根据projectId删除项目
     * @param projectId
     * @return
     */
    @DeleteMapping("/{projectId}")
    @ApiOperation(value = "删除项目方法", httpMethod = "DELETE")
    public Result DelById(@PathVariable("projectId") Integer projectId) {
        Result result = null;

        projectService.removeById(projectId);
        result = new Result("1","删除项目成功");
        return result;
    }

    /**
     * 根据projectId更新项目
     * @param projectId
     * @param project
     * @return
     */
    @PutMapping("/{projectId}")
    @ApiOperation(value = "更新项目方法", httpMethod = "PUT")
    public Result updateById(@PathVariable("projectId") Long projectId, Project project) {
        Result result = null;
        project.setId(projectId);
        // 获取userid
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        project.setCreateUser(user.getId());
        projectService.updateById(project);
        result = new Result("1",project,"更新项目成功");
        return result;
    }

    /**
     * 获取项目id和名称
     * @return
     */
    @GetMapping("/toName")
    public Result getProjectName(Integer userId) {
        List<ProjectNameVO> data = projectService.getProjectName(userId);
        return new Result("1", data);
    }
}
