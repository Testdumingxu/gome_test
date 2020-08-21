package com.gome.oa.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
 * @since 2020-08-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TestRule对象", description="")
public class TestRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "断言主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用例外键id")
    private Long caseId;

    @ApiModelProperty(value = "断言规则")
    private String rule;

    @ApiModelProperty(value = "实际结果")
    private String expression;

    @ApiModelProperty(value = "比较方式(contains,=)")
    private String operator;

    @ApiModelProperty(value = "期望结果")
    private String expected;

    @ApiModelProperty(value = "用户外键id")
    private Long createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
