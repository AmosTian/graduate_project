package org.example.graphql.demo;


import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.apache.commons.io.IOUtils;
import org.example.graphql.vo.Card;
import org.example.graphql.vo.User;
import org.omg.CORBA.Environment;

import java.io.IOException;

/**
 * @author Auspice Tian
 * @time 2021-03-21 16:15
 * @current graphql-org.example.graphql.demo
 */
public class GraphQLSDLDemo {
    public static void main(String[] args) throws IOException {

        /* 1. 读取资源，进行解析 */
        //资源名
        String fileName = "user.graphql";
        /*
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
        </dependency>
        * */
        String fileContent = IOUtils.toString(GraphQLSDLDemo.class.getClassLoader().getResource(fileName),"UTF-8");
        TypeDefinitionRegistry tyRegistry = new SchemaParser().parse(fileContent);

        /* 2. 数据查询 */
        RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
                .type("UserQuery",builder ->
                        builder.dataFetcher("user", Environment->{
                            Long id = Long.parseLong(Environment.getArgument("id"));
                            Card card = new Card("number_"+id,id);

                            return new User(id,"张三_"+id,id.intValue()+10,card);
                        })
                )
                .build();

        /* 3. 生成schema */
        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(tyRegistry,wiring);

        /* 4. 根据schema对象生成GraphQL对象 */
        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();

        String query = "{user(id:100){id,name,age,card{cardNumber}}}";
        ExecutionResult executionResult = graphQL.execute(query);

        System.out.println(executionResult.toSpecification());
    }
}
