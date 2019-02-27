package com.xuecheng.framework.domain.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryTemplateRequest {

    //模板名称
    @ApiModelProperty("模板名称")
    private String templateName;

    //模板文件信息
    @ApiModelProperty("模板文件信息")
    private String templateId;

    //站点id
    @ApiModelProperty("站台id")
    private String siteId;
}
