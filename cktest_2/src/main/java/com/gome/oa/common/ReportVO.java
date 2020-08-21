package com.gome.oa.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gome.oa.pojo.TestReport;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

@Data
public class ReportVO {
    private Integer id;
    private String name; // 套件名

    private String username; // 测试人
    private String createUser; // 执行人Id
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone="Asia/Shanghai")
    private Date createReportTime; // 生成时间

    private int totalCase; // 总用例数，计算通过率
    private int successes; // 成功通过数
    private int failures; // 失败数

    private List<CaseListVO> caseList;

    // 获取总用例数
    public int getTotalCase() {
        return caseList.size();
    }

    public int getSuccesses() {
        int count1 = 0, count2 = 0;
        for (CaseListVO caseListVO : caseList) {
            List<TestReport> reports = caseListVO.getTestReports();
            for (TestReport testReport : reports) {
                TestReport report = testReport;
                if (report != null) {
                    if (StringUtils.equals(report.getPassFlag(), "通过")) {
                        count1++;
                    } else {
                        count2++;
                    }
                }
            }
        }
        this.successes = count1;
        this.failures = count2;
        return successes;
    }

    public int getFailures() {
        return failures;
    }
}
