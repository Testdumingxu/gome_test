package com.gome.oa.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gome.oa.common.ApiListVO;
import com.gome.oa.common.ApiRunResult;
import com.gome.oa.common.ApiVO;
import com.gome.oa.pojo.Api;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dmx
 * @since 2020-07-31
 */
public interface ApiService extends IService<Api> {

    public List<ApiListVO> findAllApi(Integer userId, Page<ApiListVO> page);

    public List<ApiVO> findApiViewVO(Integer apiId);

    public List<ApiListVO> findByName(String apiName, Long userId, Page<ApiListVO> page);

    ApiRunResult run(ApiVO apiRunVO);

    public void insertApi(ApiVO apiinsert);

    void delApiById(String apiId);
}
