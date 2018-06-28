package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;


@Controller
@RequestMapping("item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/getItemById/{itemId}")
	public @ResponseBody TbItem getItemById(@PathVariable Long itemId) {
		TbItem item = itemService.getItemByid(itemId);
		return item;
	}
	@RequestMapping("/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page,Integer rows) {
		EasyUIDataGridResult itemList = itemService.getItemList(page, rows);
		return itemList;
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody	
	public TaotaoResult createItem(TbItem tbItem,String desc,String itemParams) {
		TaotaoResult createItem = itemService.createItem(tbItem, desc, itemParams);
		return createItem;
	}
	
	@RequestMapping("/page/item/{itemId}")
	public String showItemParam(@PathVariable Long itemId,Model model) {
		String paramHtml = itemService.getItemParamHtml(itemId);
		model.addAttribute("myHtml", paramHtml);
		return "itemParams";
	}
}
