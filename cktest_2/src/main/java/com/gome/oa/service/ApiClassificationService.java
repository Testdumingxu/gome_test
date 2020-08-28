package com.gome.oa.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gome.oa.common.ApiClassificationNameVo;
import com.gome.oa.common.ApiClassificationVO;
import com.gome.oa.common.ClassificationVO;
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

    public List<ClassificationVO> findAll(Integer userId, Page<ClassificationVO> page);
}
