package com.gome.oa.controller;


import com.gome.oa.common.ReportVO;
import com.gome.oa.common.Result;
import com.gome.oa.pojo.TestReport;
import com.gome.oa.pojo.User;
import com.gome.oa.service.TestReportService;
import io.swagger.models.auth.In;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dmx
 * @since 2020-08-16
 */
@RestController
@RequestMapping("/testReport")
public class TestReportController {

    @Autowired
    TestReportService testReportService;

    @RequestMapping("/run")
    public Result run(Integer suiteId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<TestReport> data = testReportService.run(user.getId().intValue(), suiteId);
        Result result = new Result("1", data, "测试执行成功");
        return result;
    }

    @RequestMapping("/findCaseRunResult")
    public Result findCaseRunResult(Integer caseId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        TestReport data = testReportService.findByCaseId(caseId);
        Result result = new Result("1", data, "测试报告");
        return result;
    }

    @GetMapping("/toSuiteId")
    public Result getSuiteId() {
        List<TestReport> data = testReportService.getSuiteId();
        return new Result("1", data);
    }

    @RequestMapping("/get")
    public Result get(Integer suiteId) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        ReportVO report = testReportService.getReport(user.getId().intValue(), suiteId);
        Result result = new Result("1", report, "测试报告成功");
        return result;
    }

}
