package com.gome.oa.common;

import lombok.Data;

/**
 * api和api_classification表的字段
 */
@Data
public class ApiListVO {
    private String id;
    private String name;
    private String method;
    private String url;
    private String description;
    private String projectId;
    private String createtime;
    private String classificationName;

}
