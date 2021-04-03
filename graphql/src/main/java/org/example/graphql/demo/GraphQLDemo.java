package org.example.graphql.demo;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.Scalars;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import org.example.graphql.vo.Card;
import org.example.graphql.vo.User;
import org.omg.CORBA.Environment;

import static graphql.Scalars.*;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * @author Auspice Tian
 * @time 2021-03-21 12:33
 * @current graphql-org.example.graphql.demo
 */
public class GraphQLDemo {
    public static void main(String[] args) {
        /**
         * 定义User对象类型
         * type User { #定义对象
         *  id:Long! # !表示该属性是非空项
         *  name:String
         *  age:Int
         * }
         * @return
         */
        GraphQLObjectType userType = newObject()
                .name("User")
                .field(newFieldDefinition().name("id").type(GraphQLLong))
                .field(newFieldDefinition().name("name").type(GraphQLString))
                .field(newFieldDefinition().name("age").type(GraphQLInt))
                .build();

        /**
         * 定义查询的类型
         * type UserQuery { #定义查询的类型
         *  user : User #指定对象
         * }
         * @return
         */
        GraphQLObjectType userQuery = newObject()
                .name("userQuery")
                .field(newFieldDefinition()
                        .name("user")
                        .argument(GraphQLArgument.newArgument()
                                .name("id").type(GraphQLLong)
                        )
                        .type(userType)
                        .dataFetcher(Environment->{
                            Long id = Environment.getArgument("id");
                            //查询数据库
                            //TODO
                            Card card = new Card("nunmber_"+id,id.longValue()+10);

                            return new User(id,"张三",id.intValue()+10,card);
                        })
                )
                .build();

        /**
         * 定义Schema
         * schema { #定义查询
         *  query: UserQuery
         * }
         * @return
         */
        GraphQLSchema graphQLSchema = GraphQLSchema.newSchema()
                .query(userQuery)
                .build();

        //构建GraphQL查询器
        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();

        String query = "{user(id:100){id,name,age}}";
        ExecutionResult executionResult = graphQL.execute(query);

        // 打印错误
        System.out.println("错误：" + executionResult.getErrors());
        // 打印数据
        System.out.println("结果：" +(Object) executionResult.toSpecification());
    }
}
