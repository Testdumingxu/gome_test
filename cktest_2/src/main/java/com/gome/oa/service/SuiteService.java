package com.gome.oa.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gome.oa.common.SuiteListVO;
import com.gome.oa.pojo.Suite;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dmx
 * @since 2020-08-11
 */
public interface SuiteService extends IService<Suite> {
    public List<SuiteListVO> findSuiteAll(Integer userId, Page<SuiteListVO> page);

    public void updateStatusBySuiteId(Integer suiteId);
}
