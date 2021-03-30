package com.haoke.api.graphql;

import graphql.schema.DataFetchingEnvironment;

/**
 * @author Auspice Tian
 * @time 2021-03-22 11:47
 * @current haoke-manage-com.haoke.api.graphql
 */
public interface MyDataFetcher {

    /**
     * 查询名称
     *
     * @return
     */
    String fieldName();

    /**
     * 具体实现数据查询的逻辑
     *
     * @param environment
     * @return
     */
    Object dataFetcher(DataFetchingEnvironment environment);
}
