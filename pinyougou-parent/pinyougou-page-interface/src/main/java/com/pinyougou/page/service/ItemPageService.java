package com.pinyougou.page.service;

/**
 * @author LiuK
 * @date 2018/12/18 9:31
 * @description: 商品详细页接口
 */
public interface ItemPageService {

    /**
     * 生成商品详细页
     * @param goodsId
     * @return
     */
    public boolean genItemHtml(Long goodsId);

    /**
     * 删除商品详情页
     * @param goodsIds
     * @return
     */
    public boolean deleteItemHtml(Long[] goodsIds);
}
