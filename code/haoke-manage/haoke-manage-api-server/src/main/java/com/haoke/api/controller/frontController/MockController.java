package com.haoke.api.controller.frontController;

import com.haoke.api.config.MockConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Auspice Tian
 * @time 2021-03-26 10:53
 * @current haoke-manage-com.haoke.api.controller
 */

@RequestMapping("mock")
@RestController
@CrossOrigin
public class MockController {

    @Autowired
    private MockConfig mockConfig;

    /**
     * 菜单
     *
     * @return
     */
    @GetMapping("homes/menu")
    public String indexMenu() {
        return this.mockConfig.getIndexMenu();
    }

    /**
     * 首页资讯
     * @return
     */
    @GetMapping("homes/info")
    public String indexInfo() {
        return this.mockConfig.getIndexInfo();
    }

    /**
     * 首页问答
     * @return
     */
    @GetMapping("homes/faq")
    public String indexFaq() {
        return this.mockConfig.getIndexFaq();
    }

    /**
     * 首页房源信息
     * @return
     */
    @GetMapping("homes/house")
    public String indexHouse() {
        return this.mockConfig.getIndexHouse();
    }

    /**
     * 查询资讯
     *
     * @param type
     * @return
     */
    @GetMapping("infos/list")
    public String infosList(@RequestParam("type")Integer type) {
        switch (type){
            case 1:
                return this.mockConfig.getInfosList1();
            case 2:
                return this.mockConfig.getInfosList2();
            case 3:
                return this.mockConfig.getInfosList3();
        }
        return this.mockConfig.getInfosList1();
    }

    /**
     * 我的中心
     * @return
     */
    @GetMapping("my/info")
    public String myInfo() {
        return this.mockConfig.getMy();
    }
}
