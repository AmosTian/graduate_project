package org.example.graphql.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Auspice Tian
 * @time 2021-03-21 16:41
 * @current graphql-org.example.graphql.vo
 */
@Data
@AllArgsConstructor
public class Card {
    private String cardNumber;
    private Long userId;
}
