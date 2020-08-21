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
 * @since 2020-08-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ApiRequestParam对象", description="")
public class ApiRequestParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "接口主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "接口外键")
    private Long apiId;

    @ApiModelProperty(value = "参数名称")
    private String name;

    @ApiModelProperty(value = "参数类型")
    private String paramType;

    @ApiModelProperty(value = "1:query 2:bodyParam(x-www-form-urlencoded) 3:head 4:body(application/json)")
    private String type;

    @ApiModelProperty(value = "实例数据")
    private String exampleData;

    @ApiModelProperty(value = "参数描述")
    private String descrition;

    @TableField(exist = false)
    private String value;

    @TableField(exist = false)
    private Integer valueId;


}
