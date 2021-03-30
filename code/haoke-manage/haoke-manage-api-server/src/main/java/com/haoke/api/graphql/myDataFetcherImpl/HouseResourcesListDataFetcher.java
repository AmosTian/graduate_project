package com.haoke.api.graphql.myDataFetcherImpl;

import com.haoke.api.graphql.MyDataFetcher;
import com.haoke.api.service.HouseResourceService;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Auspice Tian
 * @time 2021-03-22 13:22
 * @current haoke-manage-com.haoke.api.graphql.myDataFetcherImpl
 */

@Component
public class HouseResourcesListDataFetcher implements MyDataFetcher {

    @Autowired
    HouseResourceService houseResourceService;

    @Override
    public String fieldName() {
        return "HouseResourcesList";
    }

    @Override
    public Object dataFetcher(DataFetchingEnvironment environment) {
        Integer page = environment.getArgument("page");
        if(page == null){
            page = 1;
        }

        Integer pageSize = environment.getArgument("pageSize");
        if(pageSize == null){
            pageSize = 5;
        }
        return this.houseResourceService.queryList(null, page, pageSize);
    }
}
