package com.gome.oa.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gome.oa.common.ApiListVO;
import com.gome.oa.common.ApiRunResult;
import com.gome.oa.common.ApiVO;
import com.gome.oa.common.Result;
import com.gome.oa.pojo.Api;
import com.gome.oa.pojo.ApiRequestParam;
import com.gome.oa.pojo.Project;
import com.gome.oa.pojo.User;
import com.gome.oa.service.ApiRequestParamService;
import com.gome.oa.service.ApiService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dmx
 * @since 2020-07-31
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    ApiService apiService;

    @Autowired
    ApiRequestParamService apiRequestParamService;

    @GetMapping("/findAll")
    @ApiOperation(value = "查询当前用户所有Api接口", httpMethod = "GET")
    public Result getAllApi(Integer userId,Integer pageNo, Integer page_size) {

        Page<ApiListVO> page = new Page<>(pageNo == null ? 1 : pageNo,page_size == null ? 5 : page_size);
        List<ApiListVO> allApi = apiService.findAllApi(userId, page);

        Page<ApiListVO> Data = page.setRecords(allApi);
        return new Result("1", Data, "查询所有Api接口");
    }

    @GetMapping("/toApiView")
    @ApiOperation(value = "根据接口id查询出接口信息及用户名", httpMethod = "GET")
    public Result findApiViewVO(Integer apiId) {
        List<ApiVO> list = apiService.findApiViewVO(apiId);
        Result result = new Result("1",list,"success");
        return result;
    }

    /**
     * 根据接口名称模糊查询
     * @param apiName
     * @return
     */
    @GetMapping("/like")
    @ApiOperation(value = "根据接口名称模糊查询", httpMethod = "GET")
    public Result findApiByName(String apiName, Integer pageNo, Integer page_size) {
        Result result = null;
        User user = (User) SecurityUtils.getSubject().getPrincipal();


        Page<ApiListVO> page = new Page<>(pageNo == null ? 1 : pageNo,page_size == null ? 5 : page_size);
        List<ApiListVO> allApi = apiService.findByName(apiName, user.getId(),page);

        Page<ApiListVO> Data = page.setRecords(allApi);
        result = new Result("1", Data);
        return result;
    }

    @PutMapping("/edit")
    @ApiOperation(value = "根据接口id更新参数信息", httpMethod = "PUT")
    public Result toApiEdit(@RequestBody ApiVO apiEdit) {
        // 根据apiId进行更新api
        apiService.updateById(apiEdit);
        // delete原来的apiRequestParam
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("api_id", apiEdit.getId());
        apiRequestParamService.remove(queryWrapper);
        // insert apiapiRequestParam
        apiRequestParamService.saveBatch(apiEdit.getRequestParams());
        Result result = new Result("1","更新成功");
        return result;
    }

    @RequestMapping("/run")
    public Result run(@RequestBody ApiVO apiRunVO) {
        ApiRunResult apiRunResult = apiService.run(apiRunVO);
        Result result = new Result("1", apiRunResult);
        return result;
    }

    /**
     * 新增接口
     * @param apiinsert
     * @return
     */
    @PostMapping("/insert")
    public Result insertApi(@RequestBody ApiVO apiinsert) {
        apiService.insertApi(apiinsert);
        Result result = new Result("1", "新增接口成功");
        return result;
    }

    /**
     * 根据主键id删除接口
     */
    @DeleteMapping("/del/{apiId}")
    public Result delApiById(@PathVariable("apiId") String apiId) {
        apiService.delApiById(apiId);
        Result result = new Result("1", "删除接口成功");
        return result;
    }
}
