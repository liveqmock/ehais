package org.ehais.shop.model;

import java.util.Date;

/**
 * 订单状态与商品与物流状结合查询表，物流状态暂未加上
 * @author lgj628
 *
 */
public class WOrderGoodsAction {
	private Long orderId;
    private String orderSn;
    private Long userId;
    private Integer orderStatus;
    private Integer shippingStatus;
    private Integer payStatus;
    private String consignee;
    private Integer country;
    private Integer province;
    private Integer city;
    private Integer county;
    private Integer district;
    private Integer street;
    private String address;
    private String zipcode;
    private String tel;
    private String mobile;
    private String email;
    private String bestTime;
    private String signBuilding;
    private String postscript;
    private Integer shippingId;
    private String shippingName;
    private Integer payId;
    private String payName;
    private String howOos;
    private String howSurplus;
    private String packName;
    private String cardName;
    private String cardMessage;
    private String invPayee;
    private String invContent;
    private Integer goodsAmount;
    private Integer shippingFee;
    private Integer insureFee;
    private Integer payFee;
    private Integer packFee;
    private Integer cardFee;
    private Integer moneyPaid;
    private Integer surplus;
    private Integer integral;
    private Integer integralMoney;
    private Integer bonus;
    private Integer orderAmount;
    private Integer fromAd;
    private String referer;
    private Date addTime;
    private Integer confirmTime;
    private Integer payTime;
    private Integer shippingTime;
    private Integer packId;
    private Integer cardId;
    private Integer bonusId;
    private String invoiceNo;
    private String extensionCode;
    private Integer extensionId;
    private String toBuyer;
    private String payNote;
    private Integer agencyId;
    private String invType;
    private Float tax;
    private Integer isSeparate;
    private Long parentId;
    private Float discount;
    private Integer storeId;
    private String isVoid;
    private String orderSource;
    private String remark;
    
    
    
    private Long recId;
    private String subOrderSn;
    private Long goodsId;
    private String goodsName;
    private String goodsSn;
    private Long productId;
    private Integer goodsNumber;
    private Integer marketPrice;
    private Integer goodsPrice;
    private String goodsThumb;
    private Integer sendNumber;
    private Boolean isReal;
    private Short isGift;
    private String goodsAttrId;
    private Integer articleId;
    private Long sellerUserId;
    private String goodsAttr;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getShippingStatus() {
		return shippingStatus;
	}
	public void setShippingStatus(Integer shippingStatus) {
		this.shippingStatus = shippingStatus;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public Integer getCountry() {
		return country;
	}
	public void setCountry(Integer country) {
		this.country = country;
	}
	public Integer getProvince() {
		return province;
	}
	public void setProvince(Integer province) {
		this.province = province;
	}
	public Integer getCity() {
		return city;
	}
	public void setCity(Integer city) {
		this.city = city;
	}
	public Integer getCounty() {
		return county;
	}
	public void setCounty(Integer county) {
		this.county = county;
	}
	public Integer getDistrict() {
		return district;
	}
	public void setDistrict(Integer district) {
		this.district = district;
	}
	public Integer getStreet() {
		return street;
	}
	public void setStreet(Integer street) {
		this.street = street;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBestTime() {
		return bestTime;
	}
	public void setBestTime(String bestTime) {
		this.bestTime = bestTime;
	}
	public String getSignBuilding() {
		return signBuilding;
	}
	public void setSignBuilding(String signBuilding) {
		this.signBuilding = signBuilding;
	}
	public String getPostscript() {
		return postscript;
	}
	public void setPostscript(String postscript) {
		this.postscript = postscript;
	}
	public Integer getShippingId() {
		return shippingId;
	}
	public void setShippingId(Integer shippingId) {
		this.shippingId = shippingId;
	}
	public String getShippingName() {
		return shippingName;
	}
	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}
	public Integer getPayId() {
		return payId;
	}
	public void setPayId(Integer payId) {
		this.payId = payId;
	}
	public String getPayName() {
		return payName;
	}
	public void setPayName(String payName) {
		this.payName = payName;
	}
	public String getHowOos() {
		return howOos;
	}
	public void setHowOos(String howOos) {
		this.howOos = howOos;
	}
	public String getHowSurplus() {
		return howSurplus;
	}
	public void setHowSurplus(String howSurplus) {
		this.howSurplus = howSurplus;
	}
	public String getPackName() {
		return packName;
	}
	public void setPackName(String packName) {
		this.packName = packName;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCardMessage() {
		return cardMessage;
	}
	public void setCardMessage(String cardMessage) {
		this.cardMessage = cardMessage;
	}
	public String getInvPayee() {
		return invPayee;
	}
	public void setInvPayee(String invPayee) {
		this.invPayee = invPayee;
	}
	public String getInvContent() {
		return invContent;
	}
	public void setInvContent(String invContent) {
		this.invContent = invContent;
	}
	public Integer getGoodsAmount() {
		return goodsAmount;
	}
	public void setGoodsAmount(Integer goodsAmount) {
		this.goodsAmount = goodsAmount;
	}
	public Integer getShippingFee() {
		return shippingFee;
	}
	public void setShippingFee(Integer shippingFee) {
		this.shippingFee = shippingFee;
	}
	public Integer getInsureFee() {
		return insureFee;
	}
	public void setInsureFee(Integer insureFee) {
		this.insureFee = insureFee;
	}
	public Integer getPayFee() {
		return payFee;
	}
	public void setPayFee(Integer payFee) {
		this.payFee = payFee;
	}
	public Integer getPackFee() {
		return packFee;
	}
	public void setPackFee(Integer packFee) {
		this.packFee = packFee;
	}
	public Integer getCardFee() {
		return cardFee;
	}
	public void setCardFee(Integer cardFee) {
		this.cardFee = cardFee;
	}
	public Integer getMoneyPaid() {
		return moneyPaid;
	}
	public void setMoneyPaid(Integer moneyPaid) {
		this.moneyPaid = moneyPaid;
	}
	public Integer getSurplus() {
		return surplus;
	}
	public void setSurplus(Integer surplus) {
		this.surplus = surplus;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public Integer getIntegralMoney() {
		return integralMoney;
	}
	public void setIntegralMoney(Integer integralMoney) {
		this.integralMoney = integralMoney;
	}
	public Integer getBonus() {
		return bonus;
	}
	public void setBonus(Integer bonus) {
		this.bonus = bonus;
	}
	public Integer getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Integer getFromAd() {
		return fromAd;
	}
	public void setFromAd(Integer fromAd) {
		this.fromAd = fromAd;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Integer getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Integer confirmTime) {
		this.confirmTime = confirmTime;
	}
	public Integer getPayTime() {
		return payTime;
	}
	public void setPayTime(Integer payTime) {
		this.payTime = payTime;
	}
	public Integer getShippingTime() {
		return shippingTime;
	}
	public void setShippingTime(Integer shippingTime) {
		this.shippingTime = shippingTime;
	}
	public Integer getPackId() {
		return packId;
	}
	public void setPackId(Integer packId) {
		this.packId = packId;
	}
	public Integer getCardId() {
		return cardId;
	}
	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}
	public Integer getBonusId() {
		return bonusId;
	}
	public void setBonusId(Integer bonusId) {
		this.bonusId = bonusId;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getExtensionCode() {
		return extensionCode;
	}
	public void setExtensionCode(String extensionCode) {
		this.extensionCode = extensionCode;
	}
	public Integer getExtensionId() {
		return extensionId;
	}
	public void setExtensionId(Integer extensionId) {
		this.extensionId = extensionId;
	}
	public String getToBuyer() {
		return toBuyer;
	}
	public void setToBuyer(String toBuyer) {
		this.toBuyer = toBuyer;
	}
	public String getPayNote() {
		return payNote;
	}
	public void setPayNote(String payNote) {
		this.payNote = payNote;
	}
	public Integer getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}
	public String getInvType() {
		return invType;
	}
	public void setInvType(String invType) {
		this.invType = invType;
	}
	public Float getTax() {
		return tax;
	}
	public void setTax(Float tax) {
		this.tax = tax;
	}
	public Integer getIsSeparate() {
		return isSeparate;
	}
	public void setIsSeparate(Integer isSeparate) {
		this.isSeparate = isSeparate;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Float getDiscount() {
		return discount;
	}
	public void setDiscount(Float discount) {
		this.discount = discount;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public String getIsVoid() {
		return isVoid;
	}
	public void setIsVoid(String isVoid) {
		this.isVoid = isVoid;
	}
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getRecId() {
		return recId;
	}
	public void setRecId(Long recId) {
		this.recId = recId;
	}
	public String getSubOrderSn() {
		return subOrderSn;
	}
	public void setSubOrderSn(String subOrderSn) {
		this.subOrderSn = subOrderSn;
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsSn() {
		return goodsSn;
	}
	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getGoodsNumber() {
		return goodsNumber;
	}
	public void setGoodsNumber(Integer goodsNumber) {
		this.goodsNumber = goodsNumber;
	}
	public Integer getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Integer marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Integer getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(Integer goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public String getGoodsThumb() {
		return goodsThumb;
	}
	public void setGoodsThumb(String goodsThumb) {
		this.goodsThumb = goodsThumb;
	}
	public Integer getSendNumber() {
		return sendNumber;
	}
	public void setSendNumber(Integer sendNumber) {
		this.sendNumber = sendNumber;
	}
	public Boolean getIsReal() {
		return isReal;
	}
	public void setIsReal(Boolean isReal) {
		this.isReal = isReal;
	}
	public Short getIsGift() {
		return isGift;
	}
	public void setIsGift(Short isGift) {
		this.isGift = isGift;
	}
	public String getGoodsAttrId() {
		return goodsAttrId;
	}
	public void setGoodsAttrId(String goodsAttrId) {
		this.goodsAttrId = goodsAttrId;
	}
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public Long getSellerUserId() {
		return sellerUserId;
	}
	public void setSellerUserId(Long sellerUserId) {
		this.sellerUserId = sellerUserId;
	}
	public String getGoodsAttr() {
		return goodsAttr;
	}
	public void setGoodsAttr(String goodsAttr) {
		this.goodsAttr = goodsAttr;
	}
    
    
}
