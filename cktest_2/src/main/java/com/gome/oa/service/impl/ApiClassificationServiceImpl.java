package com.gome.oa.service.impl;

import com.gome.oa.common.ApiClassificationNameVo;
import com.gome.oa.common.ApiClassificationVO;
import com.gome.oa.pojo.ApiClassification;
import com.gome.oa.mapper.ApiClassificationMapper;
import com.gome.oa.service.ApiClassificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dmx
 * @since 2020-07-31
 */
@Service
public class ApiClassificationServiceImpl extends ServiceImpl<ApiClassificationMapper, ApiClassification> implements ApiClassificationService {

    @Resource
    ApiClassificationMapper apiClassificationMapper;

    @Override
    public List<ApiClassificationVO> getWithApi(Integer projectId) {
        return apiClassificationMapper.getWithApi(projectId);
    }

    @Override
    public List<ApiClassificationNameVo> findProClassificationAll(Integer userId) {
        return apiClassificationMapper.findProClassificationAll(userId);
    }
}
