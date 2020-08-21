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
 * @since 2020-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="CaseParamValue对象", description="")
public class CaseParamValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用例外键id")
    private Long caseId;

    @ApiModelProperty(value = "参数外键id")
    private Long apiRequestParamId;

    @ApiModelProperty(value = "参数值")
    private String apiRequestParamValue;

    @ApiModelProperty(value = "用户外键id")
    private Long createUser;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}
