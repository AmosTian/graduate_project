package com.haoke.api.controller.bgController;

/**
 * @author Auspice Tian
 * @time 2021-04-29 18:45
 * @current haoke-manage-com.haoke.api.controller.bgController
 */

import com.haoke.api.pojo.BGHouseResources;
import com.haoke.api.pojo.MongoHouse;
import com.haoke.api.service.HouseResourceService;
import com.haoke.api.service.MongoHouseService;
import com.haoke.api.utils.ID;
import com.haoke.api.vo.TableResult;
import com.haoke.api.vo.bgVO.BGStatusRes;
import com.haoke.api.vo.bgVO.BGTableResult;
import com.haoke.server.pojo.HouseResources;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.ArrayList;
import java.util.UUID;

@RequestMapping("bg/house/resources")
@Controller
@CrossOrigin//添加跨域
public class HousingController {

    @Autowired
    private HouseResourceService houseResourceService;

    @Autowired
    private MongoHouseService mongoHouseService;

    /**
     * 新增房源
     *
     * @param bghouseResources json数据
     * @return
     */
    @PostMapping
    @ResponseBody
    public ResponseEntity<Void> save(@RequestBody BGHouseResources bghouseResources){
        String estateId = bghouseResources.getEstateId();
        System.out.println(estateId);
        System.out.println("bghouseresourecs=>"+bghouseResources);

        MongoHouse mongoHouse = new MongoHouse(
                new ObjectId(),
                estateId,
                bghouseResources.getTitle(),
                new Float[]{bghouseResources.getLng(),bghouseResources.getLat()}
                );

        HouseResources houseResources = bghouseResources;

        System.out.println(houseResources);

        try {
            //向mongo中
            boolean bool = this.houseResourceService.save(houseResources);
            boolean flag =this.mongoHouseService.addHouseData(mongoHouse);

            if(bool && flag){
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 分页查询房源列表
     * @param houseResources
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping
    @ResponseBody
    @RequestMapping("/list")
    public ResponseEntity<BGTableResult> list(HouseResources houseResources,
                                              @RequestParam(name = "currentPage", defaultValue = "1") Integer currentPage,
                                              @RequestParam(name = "pageSize",defaultValue = "5") Integer pageSize) {

        System.out.println("currentPage ==>"+currentPage+" pageSize==>"+pageSize);
        return ResponseEntity.ok(this.houseResourceService.querybgList(houseResources, currentPage, pageSize));
    }

    /**
     * 分页查询房源列表
     * @return
     */
    @GetMapping
    @ResponseBody
    @RequestMapping("/alllist")
    public ResponseEntity<BGTableResult> alllist(HouseResources houseResources){
        System.out.println(this.houseResourceService.querybgAllList(houseResources));
        return ResponseEntity.ok(this.houseResourceService.querybgAllList(houseResources));
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

    /*
    * 删除房源
    * */
    @DeleteMapping
    @ResponseBody
    public ResponseEntity<Void> delete(@RequestBody HouseResources houseResources) {
        System.out.println(houseResources);
        try {
            int flag = this.houseResourceService.delete(houseResources);
            if (flag!=0) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
