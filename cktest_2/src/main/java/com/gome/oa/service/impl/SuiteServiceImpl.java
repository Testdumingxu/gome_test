package com.gome.oa.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gome.oa.common.SuiteListVO;
import com.gome.oa.pojo.Suite;
import com.gome.oa.mapper.SuiteMapper;
import com.gome.oa.service.SuiteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dmx
 * @since 2020-08-11
 */
@Service
public class SuiteServiceImpl extends ServiceImpl<SuiteMapper, Suite> implements SuiteService {

    @Autowired
    SuiteMapper suiteMapper;

    @Override
    public List<SuiteListVO> findSuiteAll(Integer userId, Page<SuiteListVO> page) {
        return suiteMapper.findSuiteAll(userId, page);
    }

    @Override
    public void updateStatusBySuiteId(Integer suiteId) {
        suiteMapper.updateStatusBySuiteId(suiteId);
    }
}
