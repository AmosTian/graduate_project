package org.example.graphql.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Auspice Tian
 * @time 2021-03-21 12:31
 * @current graphql-org.example.graphql.vo
 */

@Data
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private Integer age;
    private Card card;
}
