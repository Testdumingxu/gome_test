package com.gome.oa.common;

import com.gome.oa.pojo.ApiRequestParam;
import com.gome.oa.pojo.Cases;
import com.gome.oa.pojo.TestRule;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CaseEditVO extends Cases {
//    private Integer id;
//    private String name; // 用例名
//    private String createTime;
    private String method;
    private String url;
    private Integer apiId;
    private String host;
    private List<ApiRequestParam> requestParams = new ArrayList<>();
    private List<TestRule> testRules = new ArrayList<>();
}
