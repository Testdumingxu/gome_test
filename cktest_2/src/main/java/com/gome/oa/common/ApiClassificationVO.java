package com.gome.oa.common;

import com.gome.oa.pojo.Api;
import com.gome.oa.pojo.ApiClassification;
import lombok.Data;

import java.util.List;

@Data
public class ApiClassificationVO extends ApiClassification {

    // 关联对象
    List<Api> apis;
}
