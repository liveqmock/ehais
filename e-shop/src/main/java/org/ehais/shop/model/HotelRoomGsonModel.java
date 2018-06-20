package org.ehais.shop.model;

import java.io.Serializable;
import java.util.List;

public class HotelRoomGsonModel implements Serializable{

	private static final long serialVersionUID = 4782657906858574475L;
	private String nameDetail;
    private String hotelPic;
    private List<HotelRoomItemsGsonModel> items;
	public String getNameDetail() {
		return nameDetail;
	}
	public void setNameDetail(String nameDetail) {
		this.nameDetail = nameDetail;
	}
	public String getHotelPic() {
		return hotelPic;
	}
	public void setHotelPic(String hotelPic) {
		this.hotelPic = hotelPic;
	}
	public List<HotelRoomItemsGsonModel> getItems() {
		return items;
	}
	public void setItems(List<HotelRoomItemsGsonModel> items) {
		this.items = items;
	}
	
}
