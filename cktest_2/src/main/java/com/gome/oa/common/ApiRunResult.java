package com.gome.oa.common;

import lombok.Data;

@Data
public class ApiRunResult {

    private String statusCode;
    private String headers; // HttpHeaders 是MultiValueMap 需要转String
    private String body;
}
