package org.ehais.weixin.model;

import java.util.List;


public class WeiXinNotityXml {

	private String MenuId;
	private String ToUserName;
	private String FromUserName;
	private Long CreateTime;
	private String MsgType;
	private String Event;
	private String EventKey;
	private String Ticket;
	private String Content;
	private Long MsgId;
	private WeiXinImage Image;
	private Integer ArticleCount;
	private List<WeiXinArticlesItem> Articles;
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public Long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String ticket) {
		Ticket = ticket;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public Long getMsgId() {
		return MsgId;
	}
	public void setMsgId(Long msgId) {
		MsgId = msgId;
	}
	public WeiXinImage getImage() {
		return Image;
	}
	public void setImage(WeiXinImage image) {
		Image = image;
	}
	public Integer getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(Integer articleCount) {
		ArticleCount = articleCount;
	}
	public List<WeiXinArticlesItem> getArticles() {
		return Articles;
	}
	public void setArticles(List<WeiXinArticlesItem> articles) {
		Articles = articles;
	}
	public String getMenuId() {
		return MenuId;
	}
	public void setMenuId(String menuId) {
		MenuId = menuId;
	}
	
	
}
