package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiuK
 * @date 2018/12/13 20:09
 * @description:
 */
@Service(timeout = 30000)
public class ItemSearchServiceImpl implements ItemSearchService {

    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 跟据关键字搜索列表
     * @param searchMap
     * @return
     */
    @Override
    public Map<String, Object> search(Map searchMap) {

        Map<String,Object> map=new HashMap<>();
        //1.查询列表
        map.putAll(searchList(searchMap));

        //2.分组查询商品分类    
        List<String> categoryList = searchCategoryList(searchMap);
        map.put("categoryList",categoryList);

        //查询品牌规格列表
        String categoryName=(String)searchMap.get("category");

        if(!"".equals(categoryName)){//如果有分类名称
            map.putAll(searchBrandAndSpecList(categoryName));
        }else{//如果没有则按照第一个查询
            if(categoryList.size()>0){
                map.putAll(searchBrandAndSpecList(categoryList.get(0)));
            }
        }




        return  map;

    }

    /**
     * 批量导入
     * @param list
     */
    @Override
    public void importList(List list) {
        solrTemplate.saveBeans(list);
        solrTemplate.commit();
    }

    @Override
    public void deleteByGoodsIds(List goodsIdList) {
        System.out.println("删除商品ID"+goodsIdList);
        Query query=new SimpleQuery("*:*");
        Criteria criteria =new Criteria("item_goodsid").in(goodsIdList);
        query.addCriteria(criteria);
        solrTemplate.delete(query);
        solrTemplate.commit();
    }


    /**
     * 跟据关键字搜索列表
     * @param searchMap
     * @return map
     */
    private Map  searchList(Map searchMap){
        Map map=new HashMap();
        //高亮选项初始化
        HighlightQuery query=new SimpleHighlightQuery();
        HighlightOptions highlightOptions=new HighlightOptions().addField("item_title");//设置高亮的域
        highlightOptions.setSimplePrefix("<em style='color:red'>");//高亮前缀
        highlightOptions.setSimplePostfix("</em>");//高亮后缀
        query.setHighlightOptions(highlightOptions);//设置高亮选项



        //关键字空格处理
        String keywords= (String) searchMap.get("keywords");
        if(keywords.length()>0){
            searchMap.put("keywords",keywords.replace(" ",""));
        }


        //1.1按照关键字查询

        Criteria criteria= new Criteria("item_keywords").is(searchMap.get("keywords"));
        query.addCriteria(criteria);

        //1.2按商品分类过滤
        if(!"".equals(searchMap.get("category"))){
            FilterQuery filterQuery =new SimpleFilterQuery();
            Criteria filterCriteria=new Criteria("item_category").is(searchMap.get("category"));
            filterQuery.addCriteria(filterCriteria);
            query.addFilterQuery(filterQuery);
        }

        //1.3按品牌过滤
        if(!"".equals(searchMap.get("brand"))){
            FilterQuery filterQuery =new SimpleFilterQuery();
            Criteria filterCriteria=new Criteria("item_brand").is(searchMap.get("brand"));
            filterQuery.addCriteria(filterCriteria);
            query.addFilterQuery(filterQuery);
        }

        //1.4按规格查询
        if(searchMap.get("spec")!=null){
            Map<String,String> specMap= (Map<String, String>) searchMap.get("spec");
            for (String key:specMap.keySet()){
                FilterQuery filterQuery =new SimpleFilterQuery();
                Criteria filterCriteria=new Criteria("item_spec_"+key).is(specMap.get(key));
                filterQuery.addCriteria(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
        }

        //1.5按照价格区间查询
        if(!"".equals(searchMap.get("price"))){
            String[] price=((String)searchMap.get("price")).split("-");

            if(!price[0].equals("0")){//如果最低价格不等于0
                FilterQuery filterQuery =new SimpleFilterQuery();
                Criteria filterCriteria=new Criteria("item_price").greaterThanEqual(price[0]);
                filterQuery.addCriteria(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
            if(!price[1].equals("*")){//如果最高价格不等于*
                FilterQuery filterQuery =new SimpleFilterQuery();
                Criteria filterCriteria=new Criteria("item_price").lessThanEqual(price[1]);
                filterQuery.addCriteria(filterCriteria);
                query.addFilterQuery(filterQuery);
            }
        }

        //1.6分页查询
        System.out.println(searchMap.get("pageNo"));
        Integer pageNo=(Integer)searchMap.get("pageNo");//提取页码
        if(pageNo==null){
            pageNo=1;//默认第一页
        }
        Integer pageSize=(Integer)searchMap.get("pageSize");//提取每页记录数
        if(pageSize==null){
            pageSize=20;//默认第一页
        }

        //1.7排序
        String sortValue= (String) searchMap.get("sort");
        String sortField= (String) searchMap.get("sortField");
        if(sortValue!=null&&!sortField.equals("")){
            if(sortValue.equals("ASC")){//升序
                Sort sort=new Sort(Sort.Direction.ASC,"item_"+sortField);
                query.addSort(sort);
            }
            if(sortValue.equals("DESC")){//降序
                Sort sort=new Sort(Sort.Direction.DESC,"item_"+sortField);
                query.addSort(sort);
            }
        }

        query.setOffset((pageNo-1)*pageSize);//从那条记录开始查
        query.setRows(pageSize);//查多少
        /**
         * ############################ 获取高亮结果集 ################################
         */
        //高亮页对象
        HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(query, TbItem.class);
        for (HighlightEntry<TbItem> h : page.getHighlighted()) {//循环高亮入口集合
            TbItem entity = h.getEntity();//获取原实体类
            if(h.getHighlights().size()>0 && h.getHighlights().get(0).getSnipplets().size()>0){
                entity.setTitle(h.getHighlights().get(0).getSnipplets().get(0));//设置高亮结果
            }
        }
        map.put("rows",page.getContent());
        map.put("totalPages",page.getTotalPages());//返回总页数
        map.put("total",page.getTotalElements());//返回总记录数
        return map;
    }

    /**
     * 分组查询商品分类列表
     *
     */
    private List<String> searchCategoryList(Map searchMap){

        List<String> list=new ArrayList<>();

        Query query =new SimpleQuery();

        //按照关键值查询
        Criteria criteria=new Criteria("item_keywords").is(searchMap.get("keywords"));//where ...
        query.addCriteria(criteria);

        //设置分组选项
        GroupOptions groupOptions=new GroupOptions().addGroupByField("item_category");//group by ...
        query.setGroupOptions(groupOptions);

        //获取分组页
        GroupPage<TbItem> page = solrTemplate.queryForGroupPage(query,TbItem.class);

        //获取分组结果对象
        GroupResult<TbItem> groupResult = page.getGroupResult("item_category");

        //获取分组入口页
        Page<GroupEntry<TbItem>> entryList = groupResult.getGroupEntries();
        for (GroupEntry<TbItem> entry : entryList) {
            list.add(entry.getGroupValue());//将分组结果添加到返回值中

        }
        return list;
    }


    /**
     * 从缓存中查询规格和品牌列表
     * @param category
     * @return
     */
    private  Map searchBrandAndSpecList(String category){
        Map map=new HashMap();

        /**
         * 获取模板ID
         */
        Long typeId = (Long) redisTemplate.boundHashOps("itemCat").get(category);
        if(typeId!=null){
            //根据模板ID查询品牌列表
            List brandList = (List) redisTemplate.boundHashOps("brandList").get(typeId);
            map.put("brandList",brandList);//返回值添加品牌列表
            //根据模板ID查询规格列表
            List specList = (List) redisTemplate.boundHashOps("specList").get(typeId);
            map.put("specList",specList);//返回值添加规格列表
        }
        return map;
    }
}
