package com.xuecheng.framework.domain.cms.response;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.Data;

/**
 * Created by mrt on 2018/3/31.
 */
@Data
public class CmsPageResult<T> extends ResponseResult {
    T cmsPage;
    public CmsPageResult(ResultCode resultCode,T cmsPage) {
        super(resultCode);
        this.cmsPage = cmsPage;
    }
}
