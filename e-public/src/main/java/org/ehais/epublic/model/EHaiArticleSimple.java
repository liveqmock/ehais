package org.ehais.epublic.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class EHaiArticleSimple implements Serializable {
	
    private Integer articleId;
    private Integer catId;
    private String title;
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private Date articleDate;
    private String link;
    private String description;
    private String articleImages;
    private Integer readCount;
    private Integer praiseCount;
    
    private static final long serialVersionUID = 1L;
    
    
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public Integer getCatId() {
		return catId;
	}
	public void setCatId(Integer catId) {
		this.catId = catId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getArticleDate() {
		return articleDate;
	}
	public void setArticleDate(Date articleDate) {
		this.articleDate = articleDate;
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
	public String getArticleImages() {
		return articleImages;
	}
	public void setArticleImages(String articleImages) {
		this.articleImages = articleImages;
	}
	public Integer getReadCount() {
		return readCount;
	}
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}
	public Integer getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}
    
}