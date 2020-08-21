package com.gome.oa.service;

import com.gome.oa.common.ApiClassificationNameVo;
import com.gome.oa.common.ApiClassificationVO;
import com.gome.oa.pojo.ApiClassification;
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
public interface ApiClassificationService extends IService<ApiClassification> {

    public List<ApiClassificationVO> getWithApi(Integer projectId);

    public List<ApiClassificationNameVo> findProClassificationAll(Integer userId);
}
