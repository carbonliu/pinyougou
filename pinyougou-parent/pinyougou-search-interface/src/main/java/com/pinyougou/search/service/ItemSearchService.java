package com.pinyougou.search.service;

import java.util.List;
import java.util.Map;

/**
 * @author LiuK
 * @date 2018/12/13 18:41
 * @description:
 */
public interface ItemSearchService {

    /**
     * 搜索
     */
    public Map<String,Object> search(Map searchMap);

    /**
     * 导入数据
     * @param list
     */
    public void importList(List list);

    /**
     * 删除数据
     * @param goodsIdList
     */
    public void deleteByGoodsIds(List goodsIdList);
}
