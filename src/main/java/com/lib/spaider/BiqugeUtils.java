package com.lib.spaider;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 笔趣个小说：https://www.biquge.cc/html/198/198351/
 * 特征：
 *      1.存在最新章节展示（.layout.layout-col1>.section-box:last-child）最后一个列表部分
 *      2.存在分页（暂未合并相同标题）
 *方法：
 *     找到第一章的链接：正文section-box(最后一个section-box)下的a标签：（document.select(".layout.layout-col1>.section-box:last-child a[href*=html]").first();）
 *     循环读取详情页面的下一页链接内容，直至结束
 */
public class BiqugeUtils {
    //存储获取的指定的小说目录及链接地址
    private static List<Map<String,String>> novelMenuList = new ArrayList<Map<String, String>>();

    public static void  httpClientUrlInfo() throws IOException {
        int i= 0;
        String name="魔道祖师";
        long startTime = System.currentTimeMillis();
        //1. 确定首页URL: 魔道小说的第一章的URL
        String book = "/html/198/198351";
        String host = "https://www.biquge.cc";
        String indexUrl = host + book + "/1523696.html";
        String path="D:\\"+name+".txt";
        File file = new File(path);
        if(!file.exists()) {
            File dir = new File(file.getParent());
            dir.mkdirs();
            file.createNewFile();
        }
        //创建一个输出流,将爬到的小说以txt形式保存在硬盘
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
        while (true) {
            //2. 发送请求, 获取数据
            //2.1 创建httpClient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();

            //2.2 设置请求方式
            HttpGet httpGet = new HttpGet(indexUrl);
            //2.3 设置请求参数 和请求头
            httpGet.setHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");

            //2.4 发送请求, 获取响应
            CloseableHttpResponse response = httpClient.execute(httpGet);
            //2.5 获取数据

            String html = EntityUtils.toString(response.getEntity(), "UTF-8");
            //System.out.println(html);

            //3. 解析数据:
            Document document = Jsoup.parse(html);

            //获得本章的章节名称 并输出到文本中

            Elements chapterName = document.select("h1");
            bw.write(chapterName.text());
            bw.newLine();
            bw.flush();

            //获得本章的小说内容 并输出到文本中
            String p = document.select("#content").text();

            bw.write(p);
            bw.newLine();
            bw.flush();

            Element a = null;
            String nextUrl = "";
            if(i == 0){//主页-目录页
                a = document.select(".layout.layout-col1>.section-box:last-child a[href*=html]").first();
                if (a == null) {
                    break;
                }
                i++;
            }else{
                int size = document.select(".section-opt.m-bottom-opt a[href*="+book+"][href$='.html']").size();
                //i==1 排除第一章影响
                if(i==1 | size > 1){
                    //排除最后一章影响
                    if(i == 1){
                        i = 2;
                    }
                    a = document.select(".section-opt.m-bottom-opt a[href*="+book+"][href$='.html']").last();
                }
            }


            if(a == null) {
                long endTime = System.currentTimeMillis();
                System.err.println("爬取"+"《"+name+"》"+"总用时"+(endTime-startTime)/1000+"秒！");
                break;
            }
            nextUrl = a.attr("href");
            indexUrl = host + nextUrl;
            //4. 关闭httpClient对象
            httpClient.close();
        }
    }



    public static void main(String[] args) {
        try {
            httpClientUrlInfo();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
