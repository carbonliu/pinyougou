package com.pinyougou.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrandExample.Criteria;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.sellergoods.service.BrandService;

import entity.PageResult;
@Service
@Transactional
public class BrandServiceImpl implements BrandService{
	@Autowired
	private TbBrandMapper brandMapper;

	@Override
	public List<TbBrand> findAll() {
		
		return brandMapper.selectByExample(null);
	}
	/**
	 * 品牌分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 * @return
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);//分页
		Page<TbBrand> page=(Page<TbBrand>) brandMapper.selectByExample(null);
		
		return new PageResult(page.getTotal(), page.getResult());
	}
	/**
	 * 添加品牌
	 */
	@Override
	public void add(TbBrand brand) {
		brandMapper.insert(brand);
	}
	/**
	 * 根据id查找实体
	 */
	@Override
	public TbBrand findOne(long id) {
		// TODO Auto-generated method stub
		return brandMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 修改
	 */
	@Override
	public void update(TbBrand brand) {
		// TODO Auto-generated method stub
		brandMapper.updateByPrimaryKey(brand);
	}
	/**
	 * 删除
	 */
	@Override
	public void delete(long[] ids) {
		// TODO Auto-generated method stub
		if(ids!=null||ids.length>0) {
			for(long id:ids) {
				brandMapper.deleteByPrimaryKey(id);
			}
		}
	}
	/**
	 * 搜索
	 */
	@Override
	public PageResult seachPage(TbBrand brand, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);//分页
		
		TbBrandExample example=new TbBrandExample();
		
		Criteria criteria=example.createCriteria();
		if(brand!=null) {
			if(brand.getName()!=null&&brand.getName().length()>0) {
				criteria.andNameLike("%"+brand.getName()+"%");
			}
			if(brand.getFirstChar()!=null&&brand.getFirstChar().length()>0) {
				criteria.andFirstCharLike("%"+brand.getFirstChar()+"%");
			}
		}
		
		
		
		
		Page<TbBrand> page=(Page<TbBrand>) brandMapper.selectByExample(example);
		
		return new PageResult(page.getTotal(), page.getResult());
	}
	
	/**
	 * 品牌下拉框数据
	 */
	@Override
	public List<Map> selectOptionList() {
		// TODO Auto-generated method stub
		return brandMapper.selectOptionList();
	}

	

}
