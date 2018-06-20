package org.ehais.shop.model;

import java.io.Serializable;
import java.util.List;

public class HotelGsonModel implements Serializable {

	private static final long serialVersionUID = -6402619972923142784L;
	private String hotelName;
    private String hotelAddress;
    private String hotelArround;
    private Integer startPrice;
    private Integer storeId;
    private String introduce;
    private List<String> album;
    private List<HotelRoomGsonModel> room;
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getHotelAddress() {
		return hotelAddress;
	}
	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}
	public String getHotelArround() {
		return hotelArround;
	}
	public void setHotelArround(String hotelArround) {
		this.hotelArround = hotelArround;
	}
	public Integer getStartPrice() {
		return startPrice;
	}
	public void setStartPrice(Integer startPrice) {
		this.startPrice = startPrice;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public List<String> getAlbum() {
		return album;
	}
	public void setAlbum(List<String> album) {
		this.album = album;
	}
	public List<HotelRoomGsonModel> getRoom() {
		return room;
	}
	public void setRoom(List<HotelRoomGsonModel> room) {
		this.room = room;
	}
    
}


