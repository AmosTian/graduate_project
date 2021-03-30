package com.haoke.api.controller;

import com.haoke.api.service.HouseResourceService;
import com.haoke.api.vo.TableResult;
import com.haoke.server.pojo.HouseResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
     * 新增房源
     *
     * @param houseResources json数据
     * @return
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<Void> save(@RequestBody HouseResources houseResources){
        try {
            boolean bool = this.houseResourceService.save(houseResources);
            if(bool){
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

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

        return ResponseEntity.ok(this.houseResourceService.queryList(houseResources, currentPage, pageSize));
    }

    /**
     * 修改房源
     *
     * @param houseResources json数据
     * @return
     */
    @PutMapping
    @ResponseBody
    public ResponseEntity<Void> update(@RequestBody HouseResources houseResources) {
        System.out.println(houseResources);
        try {
            boolean bool = this.houseResourceService.update(houseResources);
            if (bool) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}