package com.jlfex.hermes.model;

import java.io.UnsupportedEncodingException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jlfex.hermes.common.Logger;
import com.jlfex.hermes.common.dict.Element;

/**
 * 文章信息模型
 */
@Entity
@Table(name = "hm_article")
public class Article extends Model {

	private static final long serialVersionUID = -8821546967087076070L;

	/** 文章顺序 */
	@Column(name = "order_")
	private Integer order;

	/** 文章标题 */
	@Column(name = "article_title")
	private String articleTitle;
	/** 所属分类 */
	@ManyToOne
	@JoinColumn(name = "category")
	private ArticleCategory category;
	/** 关键字 */
	@Column(name = "keywords")
	private String keywords;

	/** 文章描述 */
	@Column(name = "description")
	private String description;

	/** 文章内容 */
	@Lob
	@Column(name = "content")
	private byte[] content;

	/** 状态 */
	@Column(name = "status")
	private String status;

	/** 作者 */
	@Column(name = "author")
	private String author;
	/** 唯一标识 */
	@Column(name = "code")
	private String code;

	/**
	 * 设置标题
	 * 
	 * @param articleTitle
	 * @see #articleTitle
	 */
	public void setTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	/**
	 * 读取作者
	 * 
	 * @return
	 * @see #author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * 设置作者
	 * 
	 * @param author
	 * @see #author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * 读取日期
	 * 
	 * @return
	 * @see #datetime
	 */
	// public Date getDatetime() {
	// return datetime;
	// }
	//
	// /**
	// * 设置日期
	// *
	// * @param datetime
	// * @see #datetime
	// */
	// public void setDatetime(Date datetime) {
	// this.datetime = datetime;
	// }

	/**
	 * 读取摘要
	 * 
	 * @return
	 * @see #summary
	 */
	// public String getSummary() {
	// return summary;
	// }
	//
	// /**
	// * 设置摘要
	// *
	// * @param summary
	// * @see #summary
	// */
	// public void setSummary(String summary) {
	// this.summary = summary;
	// }

	/**
	 * 读取标志
	 * 
	 * @return
	 * @see #mark
	 */
	// public String getMark() {
	// return mark;
	// }
	//
	// /**
	// * 设置标志
	// *
	// * @param mark
	// * @see #mark
	// */
	// public void setMark(String mark) {
	// this.mark = mark;
	// }

	/**
	 * 读取状态
	 * 
	 * @return
	 * @see #status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置状态
	 * 
	 * @param status
	 * @see #status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getContent() {
		try {
			return new String(content, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Logger.error("内容编码异常", e);
			return "";
		}
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public ArticleCategory getCategory() {
		return category;
	}

	public void setCategory(ArticleCategory category) {
		this.category = category;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 状态
	 */
	public static final class Status {

		@Element("正常")
		public static final String ENABLED = "00";

		@Element("置顶")
		public static final String TOP = "10";

		@Element("失效")
		public static final String DISABLED = "99";
	}
}
