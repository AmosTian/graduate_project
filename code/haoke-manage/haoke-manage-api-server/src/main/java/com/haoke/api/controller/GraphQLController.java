package com.haoke.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionInput;
import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Auspice Tian
 * @time 2021-03-22 10:35
 * @current haoke-manage-com.haoke.api.controller
 */

@RequestMapping("graphql")
@Controller
@CrossOrigin//添加跨域
public class GraphQLController {

    @Autowired
    private GraphQL graphQL;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @GetMapping
    @ResponseBody
    public Map<String,Object> graphql(@RequestParam("query")String query,
                                      @RequestParam(value = "variables",required = false) String variablesJSON,
                                      @RequestParam(value = "operationName",required = false) String operationName) throws IOException {

        try {
            //反序列化，将JSON字符串转化为Map对象
            Map<String, Object> variables = MAPPER.readValue(variablesJSON, MAPPER.getTypeFactory().constructMapType(HashMap.class,String.class,Object.class));

            return this.executeGraphQLQuery(query,operationName,variables);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Map<String,Object> error = new HashMap<>();
        error.put("status",500);
        error.put("msg","查询出错");
        return error;
    }

    @PostMapping
    @ResponseBody
    public Map<String, Object> postGraphql(@RequestBody Map<String,Object> map) throws IOException {

        try{
            String query = (String) map.get("query");
            if(null == query){
                query = "";
            }
            String operationName = (String) map.get("operationName");
            if(null == operationName){
                operationName = "";
            }
            Map variables = (Map) map.get("variables");
            if(variables == null){
                variables = Collections.EMPTY_MAP;
            }

            return this.executeGraphQLQuery(query,operationName,variables);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String,Object> error = new HashMap<>();
        error.put("status",500);
        error.put("msg","查询出错");
        return error;
    }

    private Map<String, Object> executeGraphQLQuery(String query,String operationName,Map<String,Object> variables) {

        return this.graphQL.execute(
                ExecutionInput.newExecutionInput()
                        .query(query)
                        .variables(variables)
                        .operationName(operationName)
                        .build()
        ).toSpecification();
    }
}
