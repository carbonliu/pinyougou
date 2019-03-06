package com.pinyougou.solrutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author LiuK
 * @date 2018/12/13 14:58
 * @description: 实现商品数据的查询
 */
@Component("solrUtil")
public class SolrUtil {

    @Autowired
    private TbItemMapper  itemMapper;
    @Autowired
    private SolrTemplate solrTemplate;


    /**
     * 导入商品数据
     */
    public void importItemData(){

        TbItemExample example= new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1"); //已审核的商品信息才导入

        List<TbItem> itemList = itemMapper.selectByExample(example);
        for (TbItem item : itemList) {
            Map specMap = JSON.parseObject(item.getSpec());//将spec字段中的json字符串转化为map
            item.setSpecMap(specMap);//给带注解的字段赋值
        }
        solrTemplate.saveBeans(itemList);
        solrTemplate.commit();


    }

    public static void main(String[] args) {
        ApplicationContext context=new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
        SolrUtil solrUtil = (SolrUtil) context.getBean("solrUtil");
        solrUtil.importItemData();
    }
}
