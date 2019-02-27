//public是对axios的工具类封装,定义了HTTP请求方法
import http from './../../../base/api/public'
import querystring from 'querystring'
let sysConfig = require('@/../config/sysConfig')
let apiUrl = sysConfig.xcApiUrlPre; 

export const page_list = (page, size, params) =>  {

let query = querystring.stringify(params)
return http.requestQuickGet(apiUrl + '/cms/page/list/' + page + '/' + size + '/?' + query)
}

export const find_site = () =>  {
return http.requestQuickGet(apiUrl + '/cms/page/site')
}

export const  find_template = () =>  {
return http.requestQuickGet(apiUrl + '/cms/page/template')
}

export const page_add = (pageForm) =>  {
return http.requestPost(apiUrl + '/cms/page/add', pageForm)
}

export const page_get = id =>  {
return http.requestQuickGet(apiUrl + '/cms/page/get/' + id)
}

export const page_edit = (id, pageEdit) =>  {
return http.requestPut(apiUrl + '/cms/page/edit/' + id, pageEdit)
}

export const page_del = (id) =>  {
return http.requestDelete(apiUrl + '/cms/page/del/' + id)
}
/**
 * 发布页面
 */
export const page_postPage = (id) =>  {
return http.requestGet(apiUrl + '/cms/page/postPage/' + id)
}

/**
 *  模板管理
 * @param {*} page 页码
 * @param {*} size 页面数量
 * @param {*} params  查询参数
 */
export const template_list = (page, size, params) =>  {
let query = querystring.stringify(params)
return http.requestQuickGet(apiUrl + '/cms/template/list/' + page + '/' + size + "/?" + query)
}

/**
 * 模板文件信息
 */
export const template_files = () =>  {
return http.requestQuickGet(apiUrl + '/cms/template/files')
}


