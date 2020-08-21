package com.gome.oa.service;

import com.gome.oa.common.ReportVO;
import com.gome.oa.pojo.TestReport;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dmx
 * @since 2020-08-16
 */
public interface TestReportService extends IService<TestReport> {

    public List<TestReport> run (Integer userId, Integer suiteId);

    public List<TestReport> getSuiteId();

    TestReport findByCaseId(Integer caseId);

    ReportVO getReport(Integer userId, Integer suiteId);
}
