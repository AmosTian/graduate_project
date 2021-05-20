package com.elasticsearch.wm;

import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @author Auspice Tian
 * @time 2021-05-10 20:58
 * @current elasticSearch-com.elasticsearch.wm
 */

/*
* 1. 获取爬取的链接
* 2. 从目标网页上获取信息
* 3. 拼接从网页上获取的信息
* 4. 将数据存储到JSON文件，将图片存储到指定文件夹
* */
public class LianjiaPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(200);


    @Override
    public void process(Page page) {
        Html html = page.getHtml();

        List list = html.css(".content__list--item--title").links().all();
        page.addTargetRequests(list);

        String title = html.xpath("//div[@class='content clear w1150']/p/text()").toString();
        page.putField("title",title);
        String rent = html.xpath("//div[@class='content__aside--title']/span/text()").toString();
        page.putField("rent",rent);
        String type = html.xpath("//ul[@class='content__aside__list']/allText()").toString();
        page.putField("type",type);
        String info = html.xpath("//div[@class='content__article__info']/allText()").toString();
        page.putField("info", info);
        List<String> lis = html.xpath("//ul[@class='piclist']/li/img").all();
        String pic = StringUtils.join(lis,"|");
        page.putField("pic",pic);//从根标签下找

        if(page.getResultItems().get("title")==null){
            page.setSkip(true);//表示当前页是列表页 或者 没有房源信息，跳过爬取

            //排除列表页
            for (int i = 0; i <= 10; i++) {
                page.addTargetRequest("https://ty.lianjia.com/zufang/pg" + i);
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new LianjiaPageProcessor())
                .addUrl("https://ty.lianjia.com/zufang/")
                .thread(1)
                .addPipeline(new MyPipeline())
                .run();
    }
}
