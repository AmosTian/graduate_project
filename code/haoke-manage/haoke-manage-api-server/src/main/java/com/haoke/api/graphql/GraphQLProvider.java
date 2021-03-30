package com.haoke.api.graphql;

import graphql.GraphQL;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-03-22 10:38
 * @current haoke-manage-com.haoke.api.graphql
 */

@Component//将GraphQL对象注入IoC容器，并完成GraphQL的初始化
public class GraphQLProvider {
    private GraphQL graphQL;

    @Autowired
    private List<MyDataFetcher> myDataFetchers; //将容器中所有MyDataFetcher实现类注入

    @PostConstruct//在IoC容器初始化时运行
    public void init() throws FileNotFoundException {
        //导入graphql定义
        File file = ResourceUtils.getFile("classpath:haoke.graphql");

        //初始化graphql
        this.graphQL = GraphQL.newGraphQL(
                new SchemaGenerator().makeExecutableSchema( //schema { query: HaokeQuery}
                        new SchemaParser().parse(file),//TypeDefinitionRegistry
                        RuntimeWiring.newRuntimeWiring()//RuntimeWiring
                                .type("HaokeQuery",builder ->{//type HaokeQuery{HouseResources(id:ID):HouseResources}
                                            for (MyDataFetcher myDataFetcher : myDataFetchers) {
                                                builder.dataFetcher(myDataFetcher.fieldName(),
                                                        Environment->myDataFetcher.dataFetcher(Environment)
                                                        );
                                            }
                                            return builder;
                                        }
                                        )
                                .build()
                )
        ).build();
    }

    @Bean
    GraphQL graphQL(){
        return this.graphQL;
    }
}
