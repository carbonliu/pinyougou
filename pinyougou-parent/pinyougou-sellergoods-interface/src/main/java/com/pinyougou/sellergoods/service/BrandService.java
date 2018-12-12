package com.pinyougou.sellergoods.service;

import java.util.List;
import java.util.Map;

import com.pinyougou.pojo.TbBrand;

import entity.PageResult;

/**
 * 品牌服务层接口
 * @author LiuK
 *
 */



public interface BrandService {
	/**
	 * 返回全部列表
	 * @return
	 */
	public List<TbBrand> findAll();
	
	/**
	 * 品牌分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	/**
	 * 添加品牌
	 * @param brand
	 */
	public void add(TbBrand brand);
	/**
	 * 根据id查询品牌实体
	 * @param id
	 * @return TbBrand
	 */
	public TbBrand findOne(long id);
	/**
	 * 根据id修改品牌信息
	 * @param brand
	 */
	public void update(TbBrand brand);
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(long[] ids);
	
	/**
	 * 搜索并分页
	 * @param brand 搜索条件
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageResult seachPage(TbBrand brand,int pageNum,int pageSize);
	/**
	 * 品牌下拉框数据
	 * @return
	 */
	public List<Map> selectOptionList();
}
