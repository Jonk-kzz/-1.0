package com.xuecheng.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import com.xuecheng.manage_cms.web.config.RabbitmqConfig;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CmsPageService {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    CmsSiteRepository cmsSiteRepository;

    @Autowired
    CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    @Autowired
    RabbitTemplate rabbitTemplate;



    /**
     * 页面列表分页查询
     * @param page 当前页码
     * @param size  页面显示个数
     * @param queryPageRequest 查询条件
     * @return
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){

        //增强代码的健壮性
        if(queryPageRequest==null){
            queryPageRequest=new QueryPageRequest();
        }
        if(page<=0){
            page=1;
        }
        if(size<=0){
            size=10;
        }


        //条件匹配器
        //页面名称模糊查询,需要自定义字符串的匹配器实现模糊查询
         ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("pageName",ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("pageType",ExampleMatcher.GenericPropertyMatchers.exact());



        //条件值
         CmsPage cmsPage = new CmsPage();

        //页面别名
        if(StringUtils.isNotEmpty(queryPageRequest.getPageAliase())){
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        //页面名称
        if(StringUtils.isNotEmpty(queryPageRequest.getPageName())){
            cmsPage.setPageName(queryPageRequest.getPageName());
        }
        //页面类型
        if(StringUtils.isNotEmpty(queryPageRequest.getPageType())){
            cmsPage.setPageType(queryPageRequest.getPageType());
        }
        //站点ID
        if(StringUtils.isNotEmpty(queryPageRequest.getSiteId())){
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        //模板ID
        if(StringUtils.isNotEmpty(queryPageRequest.getTemplateId())){
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }


        //创建条件实例
         Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);


        //分页对象
         Pageable pageable = PageRequest.of(--page,size);

        //分页查询
         Page<CmsPage> cmsPages = cmsPageRepository.findAll(example,pageable);

         QueryResult<CmsPage> cmsPageQueryResult = new QueryResult<>();

        //封装结果集 进行返回
        cmsPageQueryResult.setList(cmsPages.getContent());
        //封装总记录数 进行返回
        cmsPageQueryResult.setTotal(cmsPages.getTotalElements());

        return new QueryResponseResult(CommonCode.SUCCESS,cmsPageQueryResult);
    }

    /**
     * 添加页面
     * @param cmsPage
     * @return
     */
    public CmsPageResult<CmsPage> add(CmsPage cmsPage){
        //校验页面是否存在，根据页面名称，站台Id，页面webPath查询
         CmsPage cmsPage1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());

        if(cmsPage1!=null){
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }

        if(cmsPage1==null){
            cmsPage.setPageId(null); //添加页面主键由spring data自动生成
             cmsPage = cmsPageRepository.save(cmsPage);
            //返回结果
             CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS, cmsPage);
                return cmsPageResult;
        }
        return new CmsPageResult(CommonCode.FAIL,null);

    }

    /**
     * 更新页面信息
     * @param id
     * @param cmsPage
     * @return
     */
    public CmsPageResult<CmsPage> update(String id, CmsPage cmsPage) {
        CmsPage one = this.getById(id);

        if(one!=null){
            //更新模板
            one.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            one.setPageName(cmsPage.getPageName());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //页面类型
            one.setPageType(cmsPage.getPageType());
            //数据路径
            one.setDataUrl(cmsPage.getDataUrl());

            //执行更新
             CmsPage save = cmsPageRepository.save(one);
            if(save != null){
                //返回成功
                return new CmsPageResult(CommonCode.SUCCESS, save);

            }

        }

        return new CmsPageResult<CmsPage>(CommonCode.FAIL,null);


    }

    /**
     * 删除页面
     * @param id
     * @return
     */
    public ResponseResult delete(String id) {
        cmsPageRepository.deleteById(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据Id查询页面
     * @param id 主键Id
     * @return
     */
    public CmsPage getById(String id){
         Optional<CmsPage> optional = cmsPageRepository.findById(id);

        if(optional.isPresent()){
           return optional.get();
        }

        //返回空
        return null;
    }

    /**
     * 站台列表
     * @return
     */
    public QueryResponseResult findBySite() {
         List<CmsSite> cmsSiteList = cmsSiteRepository.findAll();

        return new QueryResponseResult(CommonCode.SUCCESS,new QueryResult(cmsSiteList,cmsSiteList.size()));
    }

    /**
     * 模板列表
     * @return
     */
    public QueryResponseResult findByTemplate() {
         List<CmsTemplate> cmsTemplateList = cmsTemplateRepository.findAll();

        return new QueryResponseResult(CommonCode.SUCCESS,new QueryResult(cmsTemplateList,cmsTemplateList.size()));
    }

    /**
     * 页面静态化
     * @param pageId
     * @return
     */
    public String getPageHtml(String pageId){

        //根据页面的数据url获取不到数据
        if(pageId==null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }

        //模板
         String templateContent = getTemplateByPageId(pageId);
        if(templateContent==null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }

        //模板数据
         Map model = getModelByPageId(pageId);
        if(model==null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        //静态化
         String html = generateHtml(templateContent, model);
        if(html==null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        return html;
    }

    /**
     * 模板+数据 生产HTML
     * @param templateContent 模板
     * @param model 数据模型
     * @return
     */
    private String generateHtml(String templateContent, Map model){
        //创建配置对象 1
         Configuration configuration = new Configuration(Configuration.getVersion());
        //创建模板加载器 2
         StringTemplateLoader templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate("template",templateContent);
        //向configuration配置模板加载器 3
        configuration.setTemplateLoader(templateLoader);
        //获取模板 4
        try {
             Template template = configuration.getTemplate("template");
            //调用api进行静态化 5
             String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    /**
     * 获取页面模板
     * @param pageId
     * @return
     */
    private String getTemplateByPageId(String pageId){

         Optional<CmsPage> pageOptional = cmsPageRepository.findById(pageId);
        //根据页面id获取模板基本信息
        if (pageOptional.isPresent()){
             CmsPage cmsPage = pageOptional.get();
             String templateId = cmsPage.getTemplateId();

            //模板数据
             Optional<CmsTemplate> templateOptional = cmsTemplateRepository.findById(templateId);
            if (templateOptional.isPresent()){

                 CmsTemplate cmsTemplate = templateOptional.get();
                //模板文件id 获取GridFS中 模板
                 String templateFileId = cmsTemplate.getTemplateFileId();
                 System.out.println(templateFileId);

                //根据模板文件id 查询模板
                 GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
                //打开下载流对象
                 GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
                //创建gridFSResource ,用于获取流对象
                 GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);

                try {
                    //获取流中的数据
                     String ftl = IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");
                    return ftl;
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        }


        return null;
    }

    /**
     * 获取页面数据模板
     * @param pageId
     * @return
     */
    private Map getModelByPageId(String pageId){
        //1. 根据页面id查询 页面信息
         Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if(optional.isPresent()){
             CmsPage cmsPage = optional.get();
            //获取dataUrl
             String dataUrl = cmsPage.getDataUrl();

             ResponseEntity<Map> responseEntity = restTemplate.getForEntity(dataUrl, Map.class);
             Map map = responseEntity.getBody();

            return map;
        }


        return null;
    }

    /**
     * 页面发布
     * @param pageId 页面id
     * @return
     */
    public ResponseResult postPage(String pageId) {
        //执行静态化
         String pageHtml = this.getPageHtml(pageId);
         if(StringUtils.isEmpty(pageHtml)){
             ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
         }
         //保存静态化文件
        CmsPage cmsPage= saveHtml(pageId,pageHtml);
        //发送消息
        sendPostPage(pageId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 发送页面发布消息
     * @param pageId
     */
    private void sendPostPage(String pageId) {
        CmsPage cmsPage= this.getById(pageId);
        if(cmsPage==null){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        Map<String,String> msgMap= new HashMap<>();
        msgMap.put("pageId",pageId);
        //消息内容
        String msg= JSON.toJSONString(msgMap);
        //获取站点id作为routingKey
        String siteId=cmsPage.getSiteId();
        //发布消息
        this.rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE,siteId,msg);
    }

    /**
     * 保存静态页面内容
     * @param pageId
     * @param pageHtml
     * @return
     */
    private CmsPage saveHtml(String pageId,  String pageHtml) {
        //查询页面
        Optional<CmsPage> optional= cmsPageRepository.findById(pageId);
        if(!optional.isPresent()){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        CmsPage cmsPage= optional.get();
        //存储之前先删除
         String htmlFileId = cmsPage.getHtmlFileId();
         if(StringUtils.isNotEmpty(htmlFileId)){
             gridFsTemplate.delete(Query.query(Criteria.where("_id").is(htmlFileId)));
         }
         //保存html文件到GridFs
        try {
            InputStream inputStream = IOUtils.toInputStream(pageHtml,"UTF-8");
            ObjectId objectId= gridFsTemplate.store(inputStream,cmsPage.getPageName());
            //文件id
            String fileId=objectId.toString();
            //将文件id存储到cmsPage中
            cmsPage.setHtmlFileId(fileId);
            cmsPageRepository.save(cmsPage);
            return cmsPage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
