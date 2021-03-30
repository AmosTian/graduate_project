package com.haoke.api.service;

import com.haoke.api.config.CosConfig;
import com.haoke.api.vo.PicUploadResult;
import com.qcloud.cos.COS;
import com.qcloud.cos.COSClient;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * @author Auspice Tian
 * @time 2021-03-19 17:07
 * @current haoke-manage-com.haoke.api.service
 */

@Service
public class PicUploadTencentService {

    // 允许上传的格式
    private static final String[] IMAGE_TYPE
            = new String[] {".bmp", ".jpg",".jpeg", ".gif", ".png"};

    @Autowired
    private COSClient cosClient;//OSS客户端

    @Autowired
    private CosConfig cosConfig;

    public PicUploadResult upload(MultipartFile uploadFile) {
        // 校验图片格式
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(
                    uploadFile.getOriginalFilename(),type)) {
                //如果文件名的后缀为上述图片类型，则为合法文件，可以上传
                isLegal = true;
                break;
            }
        }

        // 封装Result对象，并且将文件的byte数组放置到result对象中
        PicUploadResult fileUploadResult = new PicUploadResult();
        if(!isLegal){
            fileUploadResult.setStatus("error");
            return fileUploadResult;
        }

        // 文件新路径
        String fileName = uploadFile.getOriginalFilename();
        String filePath = getFilePath(fileName);
        String[] filename = filePath.split("\\.");
        File localFile = null;

        // 以字节流上传到腾讯云COS
        try {
            localFile=File.createTempFile(filename[0], filename[1]);
            uploadFile.transferTo(localFile);
            localFile.deleteOnExit();

            cosClient.putObject(
                    cosConfig.getBucketName(),
                    filePath,
                    localFile
            );
        } catch (Exception e) {
            e.printStackTrace();
            //上传失败
            fileUploadResult.setStatus("error");
            return fileUploadResult;
        }

        //返回给umi的对象
        fileUploadResult.setStatus("done");
        fileUploadResult.setName(this.cosConfig.getBaseUrl() + filePath);
        fileUploadResult.setUid(String.valueOf(System.currentTimeMillis()));

        return fileUploadResult;
    }

    private String getFilePath(String fileName) {
        DateTime dateTime = new DateTime();

        return "/images/" +
                dateTime.toString("yyyy")+
                "/" + dateTime.toString("MM") + "/" +
                dateTime.toString("dd") + "/" +
                System.currentTimeMillis() +
                RandomUtils.nextInt(100, 9999) + "." +
                StringUtils.substringAfterLast(fileName, ".");
    }
}
