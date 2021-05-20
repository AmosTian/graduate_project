package com.haoke.api.service;

import com.haoke.api.vo.ESSearchResult;
import com.haoke.api.vo.es.HouseData;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WrapperQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Auspice Tian
 * @time 2021-05-11 16:56
 * @current haoke-manage-com.haoke.api.controller.frontController
 */

@Service
public class ESSearchService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public static final Integer ROWS = 10;

    public ESSearchResult search(String keyword, Integer page) {
        PageRequest pageRequest = PageRequest.of(page - 1, ROWS);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("title", keyword).operator(Operator.AND))
                .withPageable(pageRequest)
                .withHighlightFields(new HighlightBuilder.Field("title"))
                .build();
        /*
         * 高亮显示，需要指定属性映射，此时不会进行自动映射，所有的属性都必须手动映射
         * */
        AggregatedPage<HouseData> houseData = this.elasticsearchTemplate.queryForPage(searchQuery, HouseData.class,
                new SearchResultMapper() {

                    /*
                     * searchResponse : 返回结果
                     * aClass         : ES类型
                     * pageable       : 分页信息
                     * */
                    @Override
                    public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {

                        //返回结果
                        List<T> result = new ArrayList<>();

                        //返回空结果
                        if (searchResponse.getHits().totalHits == 0) {
                            return new AggregatedPageImpl<>(Collections.emptyList(), pageable, 0L);
                        }

                        //查询结果不为空
                        for (SearchHit hit : searchResponse.getHits()) {
                            //对每个结果进行处理

                            //通过反射机制，创建ORM的java对象
                            T obj = (T) ReflectUtils.newInstance(aClass);

                            try {
                                //将 id 写入
                                FieldUtils.writeField(obj, "id", hit.getId(), true);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }

                            /*
                            * ES数据
                            * {
                                    "_index": "haoke",
                                    "_type": "house",
                                    "_id": "XjbLWXkBRTe_j_yGmSm3",
                                    "_version": 1,
                                    "_score": 1,
                                    "_source": {
                                        "pic": "TY2760703226025353216_0.jpg,TY2760703226025353216_1.jpg,TY2760703226025353216_2.jpg,TY2760703226025353216_3.jpg,TY2760703226025353216_4.jpg",
                                        "orientation": "30㎡",
                                        "houseType": "房屋类型：4室0厅2卫",
                                        "rentMethod": "租赁方式：合租",
                                        "time": "随时可看",
                                        "title": " 合租·翰府 4居室 西卧 ",
                                        "rent": "800",
                                        "floor": "4/25层",
                                        "url": "https://ty.lianjia.com/zufang/TY2760703226025353216.html"
                                    }
                                }
                            * */
                            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                            for (Map.Entry<String, Object> entry : sourceAsMap.entrySet()) {
                                //对每个键值对进行映射
                                if (null == FieldUtils.getField(aClass, entry.getKey(), true)) {
                                    continue;
                                }

                                //向 obj 中写入键值对
                                try {
                                    FieldUtils.writeField(obj, entry.getKey(), entry.getValue(), true);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }

                            //处理高亮字段 覆盖
                            for (Map.Entry<String, HighlightField> stringHighlightFieldEntry : hit.getHighlightFields().entrySet()) {
                                Text[] fragments = stringHighlightFieldEntry.getValue().fragments();

                                StringBuilder sb = new StringBuilder();
                                for (Text fragment : fragments) {
                                    sb.append(fragment.toString());
                                }

                                try {
                                    FieldUtils.writeField(obj, stringHighlightFieldEntry.getKey(), sb.toString(), true);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }

                            result.add(obj);
                        }

                        return new AggregatedPageImpl<>(result, pageable, searchResponse.getHits().totalHits);
                    }
                });


        return new ESSearchResult(houseData.getTotalPages(),houseData.getContent(),null);
    }
}
