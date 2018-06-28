package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;
/**
 * 规格模板参数Service
 * @author hujy
 *
 */
@Service
public class ItemParamServiceImpl implements ItemParamService{
	
	@Autowired
	private TbItemParamMapper tbItemParamMapper;
	
	@Override
	public EasyUIDataGridResult getItemParamList(int page, int rows) {
		PageHelper.startPage(page, rows);
		TbItemParamExample tbItemParamExample = new TbItemParamExample();
		List<TbItemParam> list = tbItemParamMapper.selectByExample(tbItemParamExample);
//		System.out.println(list);
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
//		System.out.println(result);
		return result;
	}

	@Override
	public TaotaoResult getItemParamByCid(Long cid) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		if(list != null && list.size() > 0) {
			TbItemParam tbItemParam = list.get(0);
			return TaotaoResult.ok(tbItemParam);
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult addItemParam(Long cid, String param) {
		TbItemParam tbItemParam = new TbItemParam();
		tbItemParam.setItemCatId(cid);
		tbItemParam.setParamData(param);
		tbItemParam.setCreated(new Date());
		tbItemParam.setUpdated(new Date());
		tbItemParamMapper.insert(tbItemParam);
		return TaotaoResult.ok();
	}

}
