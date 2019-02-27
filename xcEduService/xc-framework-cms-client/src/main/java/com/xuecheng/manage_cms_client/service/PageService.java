package com.xuecheng.manage_cms_client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class PageService {

    @Autowired
    CmsPageRepository cmsPageRepository;

    @Autowired
    CmsSiteRepository cmsSiteRepository;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    /**
     * 将页面html保存到页面物理路径
     * @param pageId
     */
    public void  savePageToServerPath(String pageId){
         Optional<CmsPage> optional = cmsPageRepository.findById(pageId);

         if(!optional.isPresent()){
             ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
         }
         //取出页面物理路径
         CmsPage cmsPage = optional.get();
         //页面所属站点
        CmsSite cmsSite= this.getCmsSiteById(cmsPage.getSiteId());

        if(cmsSite==null){
            ExceptionCast.cast(CmsCode.CMS_SITE_NOTEXISTS);
        }

        //页面物理路径
         String pagePath = cmsSite.getSitePhysicalPath()+cmsPage.getPagePhysicalPath()+cmsPage.getPageName();
        //查询页面静态文件
        InputStream inputStream=this.getFileById(cmsPage.getHtmlFileId());
        if(inputStream==null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }

        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream=new FileOutputStream(new File(pagePath));
            IOUtils.copy(inputStream,fileOutputStream);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 根据文件id获取文件内容
     * @param htmlFileId
     * @return
     */
    private InputStream getFileById(String htmlFileId) {

        try {
             GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(htmlFileId)));

             GridFSDownloadStream gridFSDownloadStream= gridFSBucket.openDownloadStream(gridFSFile.getObjectId());

             GridFsResource gridFsResource= new GridFsResource(gridFSFile,gridFSDownloadStream);

             return gridFsResource.getInputStream();

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据站点id 得到站点
     * @param siteId 站点id
     * @return
     */
    private CmsSite getCmsSiteById(String siteId) {

         Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
         if(optional.isPresent()){
             return optional.get();
         }

        return null;
    }

}
