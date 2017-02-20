package org.ehais.weixin.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class WeiXinArticles {
	private String ToUserName;
	private String FromUserName;
	private Long CreateTime;
	private String MsgType;
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



	
}
