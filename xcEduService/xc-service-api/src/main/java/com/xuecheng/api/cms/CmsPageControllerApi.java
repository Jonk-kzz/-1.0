package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value = "cms页面管理接口",description = "cms页面管理接口,提供页面的增、删、改、查")
@RequestMapping("/cms/page")
public interface CmsPageControllerApi {

    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "页码",required = true,paramType ="path",dataType = "int",defaultValue = "1"),
            @ApiImplicitParam(name="size",value = "每页记录数",required = true,paramType = "path",dataType = "int",defaultValue = "10")
    })
    QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    @ApiOperation("添加页面")
    public CmsPageResult add(CmsPage cmsPage);

    @ApiOperation("修改页面")
    public CmsPageResult edit(String id ,CmsPage cmsPage);

    @ApiOperation("删除页面")
    public ResponseResult delete(String id);
    @ApiOperation("查询站点信息")
    public QueryResponseResult findBySite();

    @ApiOperation("查询模板信息")
    public QueryResponseResult findByTemplate();

    @ApiOperation("通过Id查询页面")
    public CmsPage findById(String id);

    @ApiOperation("发布页面")
    public ResponseResult postPage(String pageId);



}
