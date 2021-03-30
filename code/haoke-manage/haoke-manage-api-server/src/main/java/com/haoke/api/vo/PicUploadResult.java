package com.haoke.api.vo;

import lombok.Data;

/**
 * @author Auspice Tian
 * @time 2021-03-18 21:30
 * @current haoke-manage-com.haoke.api.vo
 */
@Data
public class PicUploadResult {
    // 文件唯一标识
    private String uid;
    // 文件名
    private String name;
    // 状态有：uploading done error removed
    private String status;
    // 服务端响应内容，如：'{"status": "success"}'
    private String response;
}
