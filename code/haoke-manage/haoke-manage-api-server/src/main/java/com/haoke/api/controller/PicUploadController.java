package com.haoke.api.controller;

import com.haoke.api.service.PicUploadFileSystemService;
import com.haoke.api.service.PicUploadTencentService;
import com.haoke.api.vo.PicUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Auspice Tian
 * @time 2021-03-18 21:46
 * @current haoke-manage-com.haoke.api.controller
 */
@RequestMapping("pic/upload")
@Controller
@CrossOrigin
public class PicUploadController {

    @Autowired
    private PicUploadTencentService picUploadTencentService;

    @Autowired
    private PicUploadFileSystemService picUploadService;
    /**
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping
    @ResponseBody
    public PicUploadResult upload(@RequestParam(value="file") MultipartFile file)
            throws Exception {
//        return this.picUploadService.upload(file);

        return this.picUploadTencentService.upload(file);
    }
}
