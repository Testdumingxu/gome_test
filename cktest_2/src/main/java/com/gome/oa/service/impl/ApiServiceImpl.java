package com.gome.oa.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gome.oa.common.ApiListVO;
import com.gome.oa.common.ApiRunResult;
import com.gome.oa.common.ApiVO;
import com.gome.oa.pojo.Api;
import com.gome.oa.mapper.ApiMapper;
import com.gome.oa.pojo.ApiRequestParam;
import com.gome.oa.service.ApiService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dmx
 * @since 2020-07-31
 */
@Service
public class ApiServiceImpl extends ServiceImpl<ApiMapper, Api> implements ApiService {

    @Autowired
    ApiMapper apiMapper;

    @Autowired
    ApiService apiService;

    @Override
    public List<ApiListVO> findAllApi(Integer userId, Page<ApiListVO> page) {
        return apiMapper.findAllApi(userId, page);
    }

    @Override
    public List<ApiVO> findApiViewVO(Integer apiId) {
        return apiMapper.findApiViewVO(apiId);
    }

    @Override
    public List<ApiListVO> findByName(String apiName, Long userId, Page<ApiListVO> page) {
        return apiMapper.findByName(apiName, userId, page);
    }

    @Override
    public ApiRunResult run(ApiVO apiRunVO) {
        // 远程调用
        RestTemplate restTemplate = new RestTemplate();
        String url = apiRunVO.getHost() + apiRunVO.getUrl();
        String method = apiRunVO.getMethod();
        StringBuilder getParam = new StringBuilder(url);
        List<ApiRequestParam> list = apiRunVO.getRequestParams();
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
        ApiRunResult apiRunResult = new ApiRunResult();
        String getParams = getParam.toString();
        try {
            if ("get".equalsIgnoreCase(method)) {
                httpEntity = new HttpEntity(headers);
                response = restTemplate.exchange(getParams, HttpMethod.GET, httpEntity, String.class);
            } else if ("post".equalsIgnoreCase(method)) {
                httpEntity = new HttpEntity(bodyParams, headers);
                response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
            }
            apiRunResult.setStatusCode(String.valueOf(response.getStatusCodeValue()));
            HttpHeaders headsResult = response.getHeaders();
            // 将java -- json字符串
            // 1.jackson转换方式
            // apiRunResult.setHeaders(new ObjectMapper().writeValueAsString(headsResult));
            // 2.fastjson转换方式(推荐)
            apiRunResult.setHeaders(JSON.toJSONString(headsResult));
            apiRunResult.setBody(response.getBody().toString());
        } catch (HttpStatusCodeException e) {
            // 注意此时有调用异常，有可能body有 或 没有
            apiRunResult.setStatusCode(String.valueOf(e.getRawStatusCode()));
            apiRunResult.setHeaders(JSON.toJSONString(e.getResponseHeaders()));
            apiRunResult.setBody(e.getResponseBodyAsString());
        }

        return apiRunResult;
    }

    @Override
    public void insertApi(ApiVO apiinsert) {
        apiService.save(apiinsert);
    }

    @Override
    public void delApiById(String apiId) {
        apiService.removeById(apiId);
    }
}
