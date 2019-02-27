package com.xuecheng.framework.model.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class QueryResponseResult extends ResponseResult {

    QueryResult queryResult;

    Object obj;

    public QueryResponseResult(ResultCode resultCode,QueryResult queryResult){
        super(resultCode);
       this.queryResult = queryResult;
    }

    public QueryResponseResult(ResultCode resultCode,Object obj){
        super(resultCode);
        this.obj=obj;
    }

    public QueryResponseResult(ResultCode resultCode){
        super(resultCode);
    }

}
