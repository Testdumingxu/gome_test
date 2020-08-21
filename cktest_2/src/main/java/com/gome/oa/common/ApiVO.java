package com.gome.oa.common;

import com.gome.oa.pojo.Api;
import com.gome.oa.pojo.ApiRequestParam;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiVO extends Api {

    private String createUsername;
    private String host;

    private List<ApiRequestParam> requestParams = new ArrayList<>();
    private List<ApiRequestParam> queryParams = new ArrayList<>();
    private List<ApiRequestParam> bodyParams = new ArrayList<>();
    private List<ApiRequestParam> headerParams = new ArrayList<>();
    private List<ApiRequestParam> bodyRawParams = new ArrayList<>();

}
