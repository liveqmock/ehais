package org.ehais.shop.model;

import java.io.Serializable;

public class HotelRoomItemsGsonModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8809648270651392324L;
	private String itemName;
    private String itemCat;
    private String itemBreakfast;
    private String itemWifi;
    private String itemCancel;
    private Integer itemDigit;
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemCat() {
		return itemCat;
	}
	public void setItemCat(String itemCat) {
		this.itemCat = itemCat;
	}
	public String getItemBreakfast() {
		return itemBreakfast;
	}
	public void setItemBreakfast(String itemBreakfast) {
		this.itemBreakfast = itemBreakfast;
	}
	public String getItemWifi() {
		return itemWifi;
	}
	public void setItemWifi(String itemWifi) {
		this.itemWifi = itemWifi;
	}
	public String getItemCancel() {
		return itemCancel;
	}
	public void setItemCancel(String itemCancel) {
		this.itemCancel = itemCancel;
	}
	public Integer getItemDigit() {
		return itemDigit;
	}
	public void setItemDigit(Integer itemDigit) {
		this.itemDigit = itemDigit;
	}
}
