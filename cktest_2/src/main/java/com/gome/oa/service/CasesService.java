package com.gome.oa.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gome.oa.common.ApiVO;
import com.gome.oa.common.CaseEditVO;
import com.gome.oa.common.CaseListVO;
import com.gome.oa.pojo.Cases;
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
public interface CasesService extends IService<Cases> {
    public void add(Cases caseVo, ApiVO apiRunVO);

    public List<CaseListVO> showCaseUnderUserId(Integer userId, Page<CaseListVO> page);

    public CaseEditVO findCaseEditVO(Integer caseId, Integer userId);

    public List<CaseListVO> showCaseUnderSuiteId(Integer userId, Integer suiteId, Page<CaseListVO> page);

    public void updateCase(CaseEditVO caseEditVO);
}
