package com.gome.oa.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author dmx
 * @since 2020-08-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TestReport对象", description="")
public class TestReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "报告主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用例外键id")
    private Long caseId;

    @ApiModelProperty(value = "请求地址")
    private String requestUrl;

    @ApiModelProperty(value = "请求头")
    private String requestHeaders;

    @ApiModelProperty(value = "请求主体")
    private String requestBody;

    @ApiModelProperty(value = "响应头")
    private String responseHeaders;

    @ApiModelProperty(value = "响应体")
    private String responseBody;

    @ApiModelProperty(value = "通过/不通过")
    private String passFlag;

    @TableField(exist = false)
    private Long suiteId;
}
