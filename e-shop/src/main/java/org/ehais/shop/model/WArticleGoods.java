package org.ehais.shop.model;

import java.io.Serializable;
import java.util.Date;


public class WArticleGoods implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer articleId;
    private String code;
    private String module;
    private String title;
    private String author;
    private String authorEmail;
    private Date articleDate;
    private Date startPublishDate;
    private Date endPublishDate;
    private Date startApplyDate;
    private Date endApplyDate;
    private String articleTypeCode;
    private Boolean isOpen;
    private String fileUrl;
    private Boolean openType;
    private String link;
    private String description;
    private Short sort;
    private String articleThumb;
    private String articleImages;
    private String articleLabel;
    private String videoUrl;
    private String content;
    private String articleEnum;
    
	
	private Long goodsId;
    private Integer catId;
    private String goodsSn;
    private String goodsName;
    private Integer storeId;
    private Short brandId;
    private Short goodsNumber;
    private Integer goodsWeight;
    private Integer marketPrice;
    private Integer shopPrice;
    private Integer costPrice;
    private Short warnNumber;
    private String keywords;
    private String goodsBrief;
    private String actDesc;
    private String goodsThumb;
    private String goodsImg;
    private String originalImg;
    private Byte isReal;
    private Boolean isOnSale;
    private Boolean isAloneSale;
    private Boolean isShipping;
    private Integer integral;
    private Short sortOrder;
    private Boolean isDelete;
    private Boolean isBest;
    private Boolean isNew;
    private Boolean isHot;
    private Boolean isPromote;
    private Boolean isSpecial;
    private Integer bonusTypeId;
    private Integer goodsType;
    private Integer giveIntegral;
    private Integer rankIntegral;
    private Long userId;
    private Date createDate;
    private Date updateDate;
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthorEmail() {
		return authorEmail;
	}
	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}
	public Date getArticleDate() {
		return articleDate;
	}
	public void setArticleDate(Date articleDate) {
		this.articleDate = articleDate;
	}
	public Date getStartPublishDate() {
		return startPublishDate;
	}
	public void setStartPublishDate(Date startPublishDate) {
		this.startPublishDate = startPublishDate;
	}
	public Date getEndPublishDate() {
		return endPublishDate;
	}
	public void setEndPublishDate(Date endPublishDate) {
		this.endPublishDate = endPublishDate;
	}
	public Date getStartApplyDate() {
		return startApplyDate;
	}
	public void setStartApplyDate(Date startApplyDate) {
		this.startApplyDate = startApplyDate;
	}
	public Date getEndApplyDate() {
		return endApplyDate;
	}
	public void setEndApplyDate(Date endApplyDate) {
		this.endApplyDate = endApplyDate;
	}
	public String getArticleTypeCode() {
		return articleTypeCode;
	}
	public void setArticleTypeCode(String articleTypeCode) {
		this.articleTypeCode = articleTypeCode;
	}
	public Boolean getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public Boolean getOpenType() {
		return openType;
	}
	public void setOpenType(Boolean openType) {
		this.openType = openType;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Short getSort() {
		return sort;
	}
	public void setSort(Short sort) {
		this.sort = sort;
	}
	public String getArticleThumb() {
		return articleThumb;
	}
	public void setArticleThumb(String articleThumb) {
		this.articleThumb = articleThumb;
	}
	public String getArticleImages() {
		return articleImages;
	}
	public void setArticleImages(String articleImages) {
		this.articleImages = articleImages;
	}
	public String getArticleLabel() {
		return articleLabel;
	}
	public void setArticleLabel(String articleLabel) {
		this.articleLabel = articleLabel;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getArticleEnum() {
		return articleEnum;
	}
	public void setArticleEnum(String articleEnum) {
		this.articleEnum = articleEnum;
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getCatId() {
		return catId;
	}
	public void setCatId(Integer catId) {
		this.catId = catId;
	}
	public String getGoodsSn() {
		return goodsSn;
	}
	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public Short getBrandId() {
		return brandId;
	}
	public void setBrandId(Short brandId) {
		this.brandId = brandId;
	}
	public Short getGoodsNumber() {
		return goodsNumber;
	}
	public void setGoodsNumber(Short goodsNumber) {
		this.goodsNumber = goodsNumber;
	}
	public Integer getGoodsWeight() {
		return goodsWeight;
	}
	public void setGoodsWeight(Integer goodsWeight) {
		this.goodsWeight = goodsWeight;
	}
	public Integer getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Integer marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Integer getShopPrice() {
		return shopPrice;
	}
	public void setShopPrice(Integer shopPrice) {
		this.shopPrice = shopPrice;
	}
	public Integer getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(Integer costPrice) {
		this.costPrice = costPrice;
	}
	public Short getWarnNumber() {
		return warnNumber;
	}
	public void setWarnNumber(Short warnNumber) {
		this.warnNumber = warnNumber;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getGoodsBrief() {
		return goodsBrief;
	}
	public void setGoodsBrief(String goodsBrief) {
		this.goodsBrief = goodsBrief;
	}
	public String getActDesc() {
		return actDesc;
	}
	public void setActDesc(String actDesc) {
		this.actDesc = actDesc;
	}
	public String getGoodsThumb() {
		return goodsThumb;
	}
	public void setGoodsThumb(String goodsThumb) {
		this.goodsThumb = goodsThumb;
	}
	public String getGoodsImg() {
		return goodsImg;
	}
	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}
	public String getOriginalImg() {
		return originalImg;
	}
	public void setOriginalImg(String originalImg) {
		this.originalImg = originalImg;
	}
	public Byte getIsReal() {
		return isReal;
	}
	public void setIsReal(Byte isReal) {
		this.isReal = isReal;
	}
	public Boolean getIsOnSale() {
		return isOnSale;
	}
	public void setIsOnSale(Boolean isOnSale) {
		this.isOnSale = isOnSale;
	}
	public Boolean getIsAloneSale() {
		return isAloneSale;
	}
	public void setIsAloneSale(Boolean isAloneSale) {
		this.isAloneSale = isAloneSale;
	}
	public Boolean getIsShipping() {
		return isShipping;
	}
	public void setIsShipping(Boolean isShipping) {
		this.isShipping = isShipping;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public Short getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Short sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	public Boolean getIsBest() {
		return isBest;
	}
	public void setIsBest(Boolean isBest) {
		this.isBest = isBest;
	}
	public Boolean getIsNew() {
		return isNew;
	}
	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
	public Boolean getIsHot() {
		return isHot;
	}
	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}
	public Boolean getIsPromote() {
		return isPromote;
	}
	public void setIsPromote(Boolean isPromote) {
		this.isPromote = isPromote;
	}
	public Boolean getIsSpecial() {
		return isSpecial;
	}
	public void setIsSpecial(Boolean isSpecial) {
		this.isSpecial = isSpecial;
	}
	public Integer getBonusTypeId() {
		return bonusTypeId;
	}
	public void setBonusTypeId(Integer bonusTypeId) {
		this.bonusTypeId = bonusTypeId;
	}
	public Integer getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}
	public Integer getGiveIntegral() {
		return giveIntegral;
	}
	public void setGiveIntegral(Integer giveIntegral) {
		this.giveIntegral = giveIntegral;
	}
	public Integer getRankIntegral() {
		return rankIntegral;
	}
	public void setRankIntegral(Integer rankIntegral) {
		this.rankIntegral = rankIntegral;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

    
    
    
}
