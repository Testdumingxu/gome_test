package com.gome.oa.common;

import com.gome.oa.pojo.TestReport;
import lombok.Data;

import java.util.List;

@Data
public class CaseListVO {

    private String id;
    private String name;
    private String apiId;
    private String apiUrl;
    private String suiteName;
    private String projectId;
    private String createTime;
    private List<TestReport> testReports;

}
