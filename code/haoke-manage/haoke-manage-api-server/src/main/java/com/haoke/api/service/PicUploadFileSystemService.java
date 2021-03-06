package com.haoke.api.service;

import com.haoke.api.vo.PicUploadResult;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @author Auspice Tian
 * @time 2021-03-18 21:45
 * @current haoke-manage-com.haoke.api.service
 */

@Service
public class PicUploadFileSystemService {
    // 允许上传的格式
    private static final String[] IMAGE_TYPE
            = new String[]{ ".bmp", ".jpg",".jpeg", ".gif", ".png"};

    public PicUploadResult upload(MultipartFile uploadFile) {
        // 校验图片格式,属于图片，则允许上传
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if(StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(),type)) {
                isLegal = true;
                break;
            }
        }

        // 封装Result对象，并且将文件的byte数组放置到result对象中
        PicUploadResult fileUploadResult = new PicUploadResult();
        if (!isLegal) {
            fileUploadResult.setStatus("error");
            return fileUploadResult;
        }
        // 文件新路径
        String fileName = uploadFile.getOriginalFilename();
        String filePath = getFilePath(fileName);

        // 生成图片的绝对引用地址
        String picUrl = StringUtils.replace(
                StringUtils.substringAfter(
                        filePath,"E:\\idea\\graduateProject\\code\\upload"),"\\", "/");
        fileUploadResult.setName("http://image.haoke.com" + picUrl);
        File newFile = new File(filePath);

//        try (FileOutputStream fileOutputStream = new FileOutputStream(newFile)) {
//            fileOutputStream.write(uploadFile.getBytes());
//            fileOutputStream.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//            //上传失败
//            fileUploadResult.setStatus("error");
//            return fileUploadResult;
//        }

        // 写文件到磁盘
        try {
            uploadFile.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
            //上传失败
            fileUploadResult.setStatus("error");
            return fileUploadResult;
        }
        fileUploadResult.setStatus("done");
        fileUploadResult.setUid(String.valueOf(System.currentTimeMillis()));
        return fileUploadResult;
    }

    private String getFilePath(String sourceFileName) {
        String baseFolder =
                "E:\\idea\\graduateProject\\code\\upload" +
                        File.separator +
                        "images";

        Date nowDate = new Date();
        // yyyy/MM/dd
        String fileFolder = baseFolder +
                File.separator +
                new DateTime(nowDate).toString("yyyy") +
                File.separator +
                new DateTime(nowDate).toString("MM") +
                File.separator +
                new DateTime(nowDate).toString("dd");

        File file = new File(fileFolder);
        if (!file.isDirectory()) {
            // 如果目录不存在，则创建目录
            file.mkdirs();
        }
        // 生成新的文件名
        String fileName =
                new DateTime(nowDate).toString("yyyyMMddhhmmssSSSS") +
                        RandomUtils.nextInt(100, 9999) +
                        "." +
                        StringUtils.substringAfterLast(sourceFileName, ".");
        return fileFolder + File.separator + fileName;
    }
}
