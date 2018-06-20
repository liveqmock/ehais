package org.ehais.shop.controller.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.mapper.HaiHotelMapper;
import org.ehais.shop.mapper.HaiHotelRoomItemMapper;
import org.ehais.shop.mapper.HaiHotelRoomMapper;
import org.ehais.shop.model.HaiHotel;
import org.ehais.shop.model.HaiHotelExample;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HotelWebController extends CommonController{

	
	@Autowired
	private HaiHotelMapper haiHotelMapper;
	@Autowired
	private HaiHotelRoomMapper haiHotelRoomMapper;
	@Autowired
	private HaiHotelRoomItemMapper haiHotelRoomItemMapper;
	
	@RequestMapping("/open_hotel")
	public String open_hotel(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		
		return "/project/hotel";
		
	}
	
	
	
	@ResponseBody
	@EPermissionMethod(intro="返回酒店信息数据",value="haiHotelListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiHotelListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiHotelListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "hotelRoomId", required = false) Integer hotelRoomId,
			@RequestParam(value = "hotelName", required = false) String hotelName) {
		ReturnObject<HaiHotel> rm = new ReturnObject<HaiHotel>();
		rm.setCode(0);
		
		try{
			
			HaiHotelExample example = new HaiHotelExample();
			HaiHotelExample.Criteria c = example.createCriteria();
			example.setLimitStart(condition.getStart());
			example.setLimitEnd(condition.getRows());
			example.setOrderByClause("hotel_id desc");
			if(StringUtils.isNotEmpty(hotelName))c.andHotelNameLike("%"+hotelName+"%");
			List<HaiHotel> list = haiHotelMapper.selectByExample(example);
			long total = haiHotelMapper.countByExample(example);


			rm.setCode(1);
			rm.setRows(list);
			rm.setTotal(total);
			
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("hotel", e);
			return this.errorJSON(e);
		}
	}
	
	
	
}
