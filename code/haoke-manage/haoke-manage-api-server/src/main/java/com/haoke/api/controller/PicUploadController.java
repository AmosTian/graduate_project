package com.haoke.api.controller;

import com.haoke.api.service.PicUploadFileSystemService;
import com.haoke.api.service.PicUploadTencentService;
import com.haoke.api.vo.PicUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Auspice Tian
 * @time 2021-03-18 21:46
 * @current haoke-manage-com.haoke.api.controller
 */

@RequestMapping("pic/upload")
@Controller
public class PicUploadController {

    @Autowired
    private PicUploadTencentService picUploadTencentService;

    /*@Autowired
    private PicUploadFileSystemService picUploadService;
*/
    /**
     * @param uploadFile
     * @return
     * @throws Exception
     */
    @PostMapping
    @ResponseBody
    public PicUploadResult upload(@RequestParam("file") MultipartFile uploadFile)
            throws Exception {
        //return this.picUploadService.upload(uploadFile);

        return this.picUploadTencentService.upload(uploadFile);
    }
}
