package com.example.li.springboot_crawler_demo.utils;

import com.github.kevinsawicki.http.HttpRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：Boss直聘 -- 小爬虫
 *
 * 官网github：https://github.com/kevinsawicki/http-request
 *
 * 参考：https://blog.csdn.net/u011910290/article/details/75364917
 *      https://blog.csdn.net/zhanglei500038/article/details/74858395    jsoup
 *
 * 乱码问题：https://blog.csdn.net/mycar001/article/details/78391028
 *
 * @author LJH
 * @date 2019/9/3-17,53
 * @QQ 1755497577
 */
public class CrawlerUtil {

    private String url;

    /**
     * 描述: 获取整个html
     *
     * @author LJH-1755497577 2019/9/3 19:02
     * @param url
     * @return java.lang.String
     */
    public static String getData(String url){
        //设置请求头
        Map<String,String> headers = new HashMap<>();
        headers.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        //解决乱码
        //headers.put("accept-encoding", "gzip, deflate, br");
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("cookie", "lastCity=101040100; _uab_collina=155989425705358786428292; _bl_uid=ejj1ezbR49w72poUUr06qa8iy55n; __c=1567474454; __g=-; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1565361209,1566378390,1566981114,1567474455; __l=l=%2Fwww.zhipin.com%2F&r=https%3A%2F%2Fwww.baidu.com%2Fs%3Fie%3DUTF-8%26wd%3Dboss%25E7%259B%25B4%25E8%2581%2598&friend_source=0&friend_source=0; __zp_stoken__=c688fL0GhqXlN%2FYY%2F2ydR1HFd8NS%2B8oaaNAjTZSdiGKLVMq%2BPk1q%2FaMCVkpzfOn1kk38E6u8nCHUaLXH2leUN3NrhA%3D%3D; __a=50395184.1559894257.1566981114.1567474454.68.6.4.68; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1567475125");
        headers.put("referer", "https,//www.zhipin.com/c101040100-p100101/?page=2&ka=page-2");
        headers.put("upgrade-insecure-requests", "1");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36");

        //get方法与之类似
        String content = HttpRequest.post(url)
                //设置请求头
                .headers(headers)
                .body();

//        System.out.println(content);
         return content;
    }


    /**
     * 描述: 解析html
     *      利用jsoup工具解析html
     *
     * @author LJH-1755497577 2019/9/3 19:58
     * @param data
     * @return void
     */
    public static void analysisData(String data) {

        Document doc = Jsoup.parse(data);
        Elements rows = doc.select("div[class=job-list]").get(0).select("ul");
        if (rows.size() == 0) {
            System.out.println("没有结果");
        }else {
            for (int i=0; i < rows.get(0).select("li").size(); i++){
                String addr = rows.get(0).select("li").get(i).select("div").get(0).select("div").get(0)
                        .select("p").get(0).text();
                String jobName = rows.get(0).select("li").get(i).select("div").get(0).select("div").get(0)
                        .select("h3").get(0).select("a").get(0).select("div").get(0).text();
                String jobMoney = rows.get(0).select("li").get(i).select("div").get(0).select("div").get(0)
                        .select("h3").get(0).select("a").get(0).select("span").get(0).text();

                System.out.println("--------------------------- 查询结果 ---------------------------");
                System.out.println("工作名:" + jobName);
                System.out.println("工薪:" + jobMoney);
                System.out.println("地址:" + addr);
                System.out.println("-----------------------------------------------------------------");
            }

        }

    }


    //测试执行
    public static void main(String[] args) {
//        getData("https://www.zhipin.com/c101040100-p100101/?page=2&ka=page-2");
        analysisData(getData("https://www.zhipin.com/c101040100-p100101/?page=2&ka=page-2"));
    }

}
