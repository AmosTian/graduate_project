package com.haoke.api.graphql.myDataFetcherImpl;

import com.haoke.api.graphql.MyDataFetcher;
import com.haoke.api.service.AdService;
import com.haoke.api.vo.WebResult;
import com.haoke.api.vo.ad.index.IndexAdResult;
import com.haoke.api.vo.ad.index.IndexAdResultData;
import com.haoke.server.pojo.Ad;
import com.haoke.server.vo.PageInfo;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-03-25 20:42
 * @current haoke-manage-com.haoke.api.graphql.myDataFetcherImpl
 */
@Component
public class IndexAdDataFetcher implements MyDataFetcher {

    @Autowired
    private AdService adService;

    @Override
    public String fieldName() {
        return "IndexAdList";
    }

    @Override
    public Object dataFetcher(DataFetchingEnvironment environment) {
        PageInfo<Ad> pageInfo = this.adService.queryAdList(1, 1, 3);

        List<Ad> ads = pageInfo.getRecords();

        List<IndexAdResultData> list = new ArrayList<>();
        for (Ad ad : ads) {
            list.add(new IndexAdResultData(ad.getUrl()));
        }

        return new IndexAdResult(list);
    }
}
