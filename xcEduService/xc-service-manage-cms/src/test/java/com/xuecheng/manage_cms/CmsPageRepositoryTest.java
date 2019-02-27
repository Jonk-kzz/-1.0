package com.xuecheng.manage_cms;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsPageParam;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@SpringBootTest(classes = ManageCmsApplication.class)
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    //查询
    @Test
    public void testFindPage(){
        int page=0;
         int size=10;

         PageRequest pageRequest = PageRequest.of(page, size);

         Page<CmsPage> cmsPages = cmsPageRepository.findAll(pageRequest);

        System.out.println(cmsPages);

    }
    //添加
    @Test
    public void testInsert(){
         CmsPage cmsPage = new CmsPage();
         cmsPage.setSiteId("s01");
         cmsPage.setTemplateId("t01");
         cmsPage.setPageName("测试页面");
         cmsPage.setPageCreateTime(new Date());


         List<CmsPageParam> cmsPageParams = new ArrayList<>();

        CmsPageParam cmsPageParam = new CmsPageParam();

        cmsPageParam.setPageParamName("param1");
        cmsPageParam.setPageParamValue("value1");

        cmsPageParams.add(cmsPageParam);
        cmsPage.setPageParams(cmsPageParams);

        cmsPageRepository.save(cmsPage);
        System.out.println(cmsPage);

    }

    //删除
    @Test
    public void testDelete(){
        cmsPageRepository.deleteById("5c0f3a00dfe58eaa14356ca0");

    }

    //修改
    @Test
    public void testUpdate(){


         CmsPage cmsPage1 = new CmsPage();
         cmsPage1.setSiteId("s01");
        Example example=Example.of(cmsPage1);
        Optional<CmsPage> optional = cmsPageRepository.findOne(example);
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            cmsPage.setPageName("测试页面01");
            cmsPageRepository.save(cmsPage);
        }
    }

    @Test
    public void testFindAll(){
        //条件匹配器
         ExampleMatcher exampleMatcher = ExampleMatcher.matching();
           // exampleMatcher.withMatcher("pageAlise",ExampleMatcher.GenericPropertyMatchers.contains());
            exampleMatcher.withMatcher("siteId",ExampleMatcher.GenericPropertyMatchers.contains());
            //exampleMatcher.withMatcher("templateId",ExampleMatcher.GenericPropertyMatchers.contains());
            //页面别名模糊查询,需要自定义字符串的匹配器实现模糊查询
            //ExampleMatcher.GenericPropertyMatchers.contains() 包含
            //ExampleMatcher.GenericPropertyMatchers.startsWith() //开头匹配
            //条件值
            CmsPage cmsPage = new CmsPage();
            //站点ID
            cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
            //模板ID
            cmsPage.setTemplateId("5a962b52b00ffc514038faf7");
                cmsPage.setPageAliase("首页");
                //创建条件实例

        final Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        final PageRequest pageRequest = PageRequest.of(0, 10);

        final Page<CmsPage> all = cmsPageRepository.findAll(example, pageRequest);
        final List<CmsPage> content = all.getContent();
        System.out.println(content);
        System.out.println(all);


    }

    /**
     * 读取轮播图模板数据
     */
    @Test
    public void testRestTemplate(){
         ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f", Map.class);
        System.out.println(forEntity);
    }

    /**
     * 读取GridFS模板信息
     *
     */
    @Test
    public void queryFile() throws IOException {
        String fileId="5a795bbcdd573c04508f3a59";
        //根据id查询文件
        final GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        //打开下载流对象
        final GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //创建gridFsResource , 用于获取流对象
        final GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        //获取流中的数据
        final String s = IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");

        System.out.println(s);


    }


}
