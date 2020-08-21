package com.gome.oa.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.gome.oa.common.ApiRunResult;
import com.gome.oa.common.CaseEditVO;
import com.gome.oa.common.ReportVO;
import com.gome.oa.pojo.ApiRequestParam;
import com.gome.oa.pojo.TestReport;
import com.gome.oa.mapper.TestReportMapper;
import com.gome.oa.pojo.TestRule;
import com.gome.oa.pojo.User;
import com.gome.oa.service.TestReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Case;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dmx
 * @since 2020-08-16
 */
@Service
public class TestReportServiceImpl extends ServiceImpl<TestReportMapper, TestReport> implements TestReportService {
    
    @Autowired
    TestReportMapper testReportMapper;
    
    @Override
    public List<TestReport> run(Integer userId, Integer suiteId) {

        List<TestReport> reportList = new ArrayList<>();
        // 1.先根据suiteId查询
        List<CaseEditVO> list = testReportMapper.findCaseEditVoUnderSuite(userId, suiteId);
        for (CaseEditVO caseEditVO : list) {
            //2. 远程调用api执行
            TestReport report= runAndGetReport(caseEditVO);
            reportList.add(report);

        }
        // 3.对test_report先删除在输入
        testReportMapper.deleteReport(suiteId);
        this.saveBatch(reportList);
        return reportList;
    }

    TestReport runAndGetReport(CaseEditVO caseEditVO) {
        TestReport testReport = new TestReport();
        RestTemplate restTemplate = new RestTemplate();
        String url = caseEditVO.getHost() + caseEditVO.getUrl();
        String method = caseEditVO.getMethod();
        StringBuilder getParam = new StringBuilder(url);
        List<ApiRequestParam> list = caseEditVO.getRequestParams();
        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        LinkedMultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();

        for (ApiRequestParam apiRequestParam : list) {
            if ("3".equals(apiRequestParam.getType())) { // type类型为3的:头
                headers.add(apiRequestParam.getName(), apiRequestParam.getValue());
            } else if ("1".equals(apiRequestParam.getType())) {
                // get请求拼接请求参数到url后面 ?key=value&key=value
                if (getParam.lastIndexOf("?") == -1) {
                    getParam.append("?").append(apiRequestParam.getName() + "=" + apiRequestParam.getValue());
                } else {
                    getParam.append("&").append(apiRequestParam.getName() + "=" + apiRequestParam.getValue());
                }

            } else {
                // body 2 4  注意此时type==1的情况未处理
                bodyParams.add(apiRequestParam.getName(), apiRequestParam.getValue());
            }
        }

        HttpEntity httpEntity = null;
        ResponseEntity response = null;


        String getParams = getParam.toString();
        try {
            if ("get".equalsIgnoreCase(method)) {
                httpEntity = new HttpEntity(headers);
                response = restTemplate.exchange(getParams, HttpMethod.GET, httpEntity, String.class);
            } else if ("post".equalsIgnoreCase(method)) {
                httpEntity = new HttpEntity(bodyParams, headers);
                testReport.setRequestBody(JSON.toJSONString(bodyParams));
                response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            }
            testReport.setCaseId(caseEditVO.getId());
            testReport.setSuiteId(caseEditVO.getSuiteId());
            testReport.setRequestUrl(url);
            testReport.setRequestHeaders(JSON.toJSONString(headers));
            testReport.setResponseHeaders(JSON.toJSONString(response.getHeaders()));
            testReport.setResponseBody(response.getBody().toString());
            testReport.setPassFlag(assertByTestRule(response.getBody().toString(),caseEditVO.getTestRules()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return testReport;
    }

    /**
     * 判断通过/不通过
     * @param responseBody 请求主体
     * @param testRules 验证规则
     * @return
     */
    String assertByTestRule(String responseBody, List<TestRule> testRules) {
        boolean flag = true;
        for (TestRule testRule : testRules) {
            Object value = JSONPath.read(responseBody, testRule.getExpression());// $.name
            String actual = (String) value;
            String op = testRule.getOperator();
            if (StringUtils.equals("=", op) && StringUtils.isNotBlank(actual)) {
                if (!StringUtils.equals(actual, testRule.getExpected())) {
                    flag = false;
                }
            }
            if (StringUtils.equals("contains", op) && StringUtils.isNotBlank(actual)) {
                if (!StringUtils.contains(actual, testRule.getExpected())) {
                    flag = false;
                }
            }
            if (!flag) {
                return "不通过";
            }
        }
        return "通过";
    }

    @Override
    public List<TestReport> getSuiteId() {
        return testReportMapper.getSuiteId();
    }

    @Override
    public TestReport findByCaseId(Integer caseId) {
        return testReportMapper.findByCaseId(caseId);
    }

    @Override
    public ReportVO getReport(Integer userId, Integer suiteId) {
        ReportVO report = testReportMapper.getReport(userId, suiteId);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        report.setUsername(user.getRealname());
        report.setCreateReportTime(new Date());
        return report;
    }
}
