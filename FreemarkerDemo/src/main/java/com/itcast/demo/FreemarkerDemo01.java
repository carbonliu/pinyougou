package com.itcast.demo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * @author LiuK
 * @date 2018/12/17 18:35
 * @description:
 */
public class FreemarkerDemo01 {
    public static void main(String[] args) throws IOException, TemplateException {
        //1.创建配置类
        Configuration configuration=new Configuration(Configuration.getVersion());
        //2.设置模板所在目录
        configuration.setDirectoryForTemplateLoading(new File("D:\\JAVA\\CODE\\pinyougou\\FreemarkerDemo\\src\\main\\resources"));
        //3.设置字符集
        configuration.setDefaultEncoding("utf-8");
        //4.加载模板
        Template template = configuration.getTemplate("test.ftl");
        //5.创建数据类型
        Map map=new HashMap();
        map.put("name","张三");
        map.put("message","欢迎使用静态网页解决方案:freemarker");
        map.put("success",false);

        List goodsList=new ArrayList();
        Map goods1=new HashMap();
        goods1.put("name", "苹果");
        goods1.put("price", 5.8);
        Map goods2=new HashMap();
        goods2.put("name", "香蕉");
        goods2.put("price", 2.5);
        Map goods3=new HashMap();
        goods3.put("name", "橘子");
        goods3.put("price", 3.2);
        goodsList.add(goods1);
        goodsList.add(goods2);
        goodsList.add(goods3);
        map.put("goodsList", goodsList);
        map.put("today",new Date());
        map.put("point",123443234);
        //6.创建writer对象
        Writer out=new FileWriter(new File("e:\\test.html"));
        //7.输出
        template.process(map,out);
        //关闭输出流
        out.close();

    }
}
