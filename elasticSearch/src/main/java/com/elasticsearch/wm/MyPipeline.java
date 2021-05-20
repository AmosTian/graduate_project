package com.elasticsearch.wm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Auspice Tian
 * @time 2021-05-10 22:11
 * @current elasticSearch-com.elasticsearch.wm
 */
public class MyPipeline implements Pipeline {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void process(ResultItems resultItems, Task task) {
        Map<String,Object> data = new HashMap<>();

        data.put("url",resultItems.getRequest().getUrl());//目标链接
        data.put("title",resultItems.get("title"));//title
        data.put("rent", resultItems.get("rent"));//租金

        String[] types = StringUtils.split(resultItems.get("type"), ' ');
        data.put("rentMethod", types[0]);//租赁方式
        data.put("houseType", types[1]);//户型，如：2室1厅1卫
        data.put("orientation", types[2]);//朝向

        String[] infos = StringUtils.split(resultItems.get("info"), ' ');
        for (String info : infos) {
            if (StringUtils.startsWith(info, "看房：")) {
                data.put("time", StringUtils.split(info, '：')[1]);
            } else if (StringUtils.startsWith(info, "楼层：")) {
                data.put("floor", StringUtils.split(info, '：')[1]);
            }
        }

        List<String> imageList = new ArrayList<>();
        String[] pics = StringUtils.split(resultItems.get("pic"), '|');
        String baseName = StringUtils.substringBefore(StringUtils.substringAfterLast(resultItems.getRequest().getUrl(), "/"), ".");
        String pic = "";
        try {
            for (int i = 0; i < pics.length; i++) {
               String url = StringUtils.join(StringUtils.split(pics[i], '"')[1]);
               String newName = baseName + "_" +i + ".jpg";
               this.downloadFile(url, new File("E:\\idea\\graduateProject\\tylj-images\\" + newName));
               imageList.add(newName);
            }
            String imageUrl = StringUtils.join(imageList,",");
            data.put("image",imageUrl);
            String json = MAPPER.writeValueAsString(data);
            FileUtils.write(new File("E:\\idea\\graduateProject\\tylj-data.json"), json + "\n", "UTF-8", true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 下载文件
     * @param url 文件url
     * @param dest 目标目录
     */
    public void downloadFile(String url, File dest) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(httpGet);

        try {
            FileUtils.writeByteArrayToFile(dest, IOUtils.toByteArray(response.getEntity().getContent()));
        } finally {
            response.close();
        }
    }
}
