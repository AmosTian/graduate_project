package com.mybatisplus.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.sound.midi.Soundbank;

/**
 * @author Auspice Tian
 * @time 2021-03-15 16:38
 * @current mybatis-plus-com.mybatisplus.pojo
 */
@Builder
@ToString
public class Item {
    private Long id;
    private Long price;

    public static void main(String[] args) {
        Item item = Item.builder().id(1L).price(2800L).build();
        System.out.println(item);
    }
}
