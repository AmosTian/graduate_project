package com.mydubbo.service;

/**
 * @author Auspice Tian
 * @time 2021-03-12 11:17
 * @current mydubbo-com.mydubbo.service
 */
import com.mydubbo.pojo.User;

import java.util.List;

public interface UserService {

    /**
     * 查询所有的用户数据
     *
     * @return
     */
    List<User> queryAll();
}

