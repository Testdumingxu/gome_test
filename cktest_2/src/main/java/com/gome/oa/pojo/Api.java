package com.gome.oa.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2020-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Api对象", description="")
public class Api implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "接口主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "接口分类外键id")
    private Long apiClassificationId;

    @ApiModelProperty(value = "接口名称")
    private String name;

    @ApiModelProperty(value = "请求方法")
    private String method;

    @ApiModelProperty(value = "请求资源地址")
    private String url;

    @ApiModelProperty(value = "接口描述")
    private String description;

    @ApiModelProperty(value = "用户外键id")
    private Long createUser;

    @ApiModelProperty(value = "项目外键id")
    private Long projectId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="Asia/Shanghai")
    private Date createTime;


}
