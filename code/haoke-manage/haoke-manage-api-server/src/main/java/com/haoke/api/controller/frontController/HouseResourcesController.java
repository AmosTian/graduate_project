package com.haoke.api.controller.frontController;

import com.haoke.api.service.HouseResourceService;
import com.haoke.api.vo.TableResult;
import com.haoke.server.pojo.HouseResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.Console;

/**
 * @author Auspice Tian
 * @time 2021-03-16 21:59
 * @current haoke-manage-com.haoke.api.controller
 */
@RequestMapping("house/resources")
@Controller
public class HouseResourcesController {

    @Autowired
    private HouseResourceService houseResourceService;

    /**
     * 查询房源列表
     * @param houseResources
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping
    @ResponseBody
    @RequestMapping("/list")
    public ResponseEntity<TableResult> list(HouseResources houseResources,
            @RequestParam(name = "currentPage", defaultValue = "1") Integer currentPage,
            @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize) {

        System.out.println("currentPage ==>"+currentPage+" pageSize==>"+pageSize);
        return ResponseEntity.ok(this.houseResourceService.queryList(houseResources, currentPage, pageSize));
    }
}