package org.ehais.shop.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.shop.mapper.HaiGoodsGalleryMapper;
import org.ehais.shop.mapper.HaiHotelMapper;
import org.ehais.shop.mapper.HaiHotelRoomItemMapper;
import org.ehais.shop.mapper.HaiHotelRoomMapper;
import org.ehais.shop.model.HaiGoodsGallery;
import org.ehais.shop.model.HaiHotel;
import org.ehais.shop.model.HaiHotelExample;
import org.ehais.shop.model.HaiHotelRoom;
import org.ehais.shop.model.HaiHotelRoomItem;
import org.ehais.shop.model.HotelGsonModel;
import org.ehais.shop.model.HotelRoomGsonModel;
import org.ehais.shop.model.HotelRoomItemsGsonModel;
import org.ehais.tools.ReturnObject;
import org.ehais.util.Bean2Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;


@Controller
@RequestMapping("/openapi")
public class HotelAjaxApiController extends CommonController{

	@Autowired
	private HaiHotelMapper haiHotelMapper;
	@Autowired
	private HaiHotelRoomMapper haiHotelRoomMapper;
	@Autowired
	private HaiHotelRoomItemMapper haiHotelRoomItemMapper;
	@Autowired
	private HaiGoodsGalleryMapper haiGoodsGalleryMapper;
	
	
	@ResponseBody
	@RequestMapping(value="/hotel_ajax_api",method=RequestMethod.POST)
	public String hotel_ajax_api(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "json", required = true) String json){
		
		ReturnObject<HaiHotel> rm = new ReturnObject<HaiHotel>();
		rm.setCode(0);
		try {
			Gson gson = new Gson();
			HotelGsonModel hotel = gson.fromJson(json, HotelGsonModel.class);//对于javabean直接给出class实例
	        System.out.println(Bean2Utils.printEntity(hotel));
	        
	        HaiHotel hotelModel = new HaiHotel();
	        hotelModel.setHotelName(hotel.getHotelName());
	        hotelModel.setHotelAddress(hotel.getHotelAddress());
	        hotelModel.setHotelArround(hotel.getHotelArround());
	        hotelModel.setIntroduce(hotel.getIntroduce());
	        hotelModel.setStartPrice(Integer.valueOf(hotel.getStartPrice()));
	        hotelModel.setStoreId(store_id);
	        
	        HaiHotelExample hotelExample = new HaiHotelExample();
	        hotelExample.createCriteria().andStoreIdEqualTo(store_id).andHotelNameEqualTo(hotel.getHotelName());
			Long c = haiHotelMapper.countByExample(hotelExample);
			if(c > 0) {
				rm.setMsg(hotel.getHotelName()+":记录存在了");
				return this.writeJson(rm);
			}
			haiHotelMapper.insert(hotelModel);
			
			List<String> album = hotel.getAlbum();
			for (String string : album) {
				HaiGoodsGallery ga = new HaiGoodsGallery();
				ga.setImgOriginal(string);
				ga.setThumbUrl(string);
				ga.setImgUrl(string);
				ga.setTableName("hai_hotel");
				ga.setGoodsId(Long.valueOf(hotelModel.getHotelId().toString()));
				ga.setImgDesc("");
				ga.setStoreId(store_id);
				
				haiGoodsGalleryMapper.insert(ga);
			}
	        
	        List<HotelRoomGsonModel> room = hotel.getRoom();
	        for (HotelRoomGsonModel hrg : room) {
				HaiHotelRoom hr = new HaiHotelRoom();
				hr.setHotelId(hotelModel.getHotelId());
				hr.setNameDetail(hrg.getNameDetail());
				hr.setHotelPic(hrg.getHotelPic());
				
				haiHotelRoomMapper.insert(hr);
				
				List<HotelRoomItemsGsonModel> items = hrg.getItems();
				for (HotelRoomItemsGsonModel hrig : items) {
					HaiHotelRoomItem h = new HaiHotelRoomItem();
					h.setHotelRoomId(hr.getHotelRoomId());
					h.setItemName(hrig.getItemName());
					h.setItemCat(hrig.getItemCat());
					h.setItemBreakfast(hrig.getItemBreakfast());
					h.setItemWifi(hrig.getItemWifi());
					h.setItemCancel(hrig.getItemCancel());
					h.setItemDigit(hrig.getItemDigit());
					
					haiHotelRoomItemMapper.insert(h);
				}
			}
	        
	        rm.setCode(1);
	        rm.setMsg("success");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return this.writeJson(rm);
	}
}
