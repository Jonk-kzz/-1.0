package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryTemplateRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(value = "cms模板管理接口",description = "cms模板管理接口,提供页面的增、删、改、查")
@RequestMapping("/cms/template")
public interface CmsTemplateControllerApi {

    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "页码",required = true,paramType ="path",dataType = "int",defaultValue = "1"),
            @ApiImplicitParam(name="size",value = "每页记录数",required = true,paramType = "path",dataType = "int",defaultValue = "10")
    })
    QueryResponseResult findList(int page, int size, QueryTemplateRequest queryTemplateRequest);

    @ApiOperation("添加页面")
    public CmsPageResult add(CmsTemplate cmsTemplate);

    @ApiOperation("修改页面")
    public CmsPageResult edit(String id ,CmsTemplate cmsTemplate);

    @ApiOperation("删除页面")
    public ResponseResult delete(String id);

    @ApiOperation("模板文件信息")
    List<CmsPage> files();

    @ApiOperation("上传ftl文件")
    String upload(MultipartFile multipartFile);
}
