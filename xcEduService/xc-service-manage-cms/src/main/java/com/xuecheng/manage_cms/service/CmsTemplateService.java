package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryTemplateRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsTemplateService {

    @Autowired
    CmsTemplateRepository cmsTemplateRepository;

    @Autowired
    CmsPageRepository cmsPageRepository;

    public QueryResponseResult findList(int page, int size, QueryTemplateRequest queryTemplateRequest) {

        if(page<=0){
            page=1;
        }
        if(size<=0){
            size=10;
        }
        CmsTemplate cmsTemplate=new CmsTemplate();

        //模板名称
        if(StringUtils.isNotEmpty(queryTemplateRequest.getTemplateName())){
            cmsTemplate.setTemplateName(queryTemplateRequest.getTemplateName());
        }
        //模板文件信息
        if(StringUtils.isNotEmpty(queryTemplateRequest.getTemplateId())){
            cmsTemplate.setTemplateId(queryTemplateRequest.getTemplateId());
        }
        //站点名称
        if(StringUtils.isNotEmpty(queryTemplateRequest.getSiteId())){
            cmsTemplate.setSiteId(queryTemplateRequest.getSiteId());
        }

        ExampleMatcher exampleMatcher= ExampleMatcher.matching().withMatcher("templateName",ExampleMatcher.GenericPropertyMatchers.contains());

        Example<CmsTemplate>  example =Example.of(cmsTemplate,exampleMatcher);

        Pageable pageable=PageRequest.of(--page,size);

        Page<CmsTemplate> pages= cmsTemplateRepository.findAll(example,pageable);

        return  new QueryResponseResult(CommonCode.SUCCESS,new QueryResult(pages.getContent(),pages.getTotalElements()));
    }

    public List<CmsPage> files() {
         List<CmsPage> list = cmsPageRepository.findAll();
        return list;
    }
}
