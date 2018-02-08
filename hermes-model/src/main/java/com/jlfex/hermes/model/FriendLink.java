package com.jlfex.hermes.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 友情链接和banner信息模型
 */
@Entity
@Table(name = "hm_friend_link")
public class FriendLink extends Model {

	private static final long serialVersionUID = 8426583187024867914L;

	/** 名称 */
	@Column(name = "name")
	private String name;

	/** 链接 */
	@Column(name = "link")
	private String link;

	/** 图片 */
	@Column(name = "image")
	private byte[] image;

	/** 顺序 */
	@Column(name = "order_")
	private Integer order;

	/** 状态 */
	@Column(name = "status")
	private String status;
	/** 类型 */
	@Column(name = "type")
	private String type;// 1,友情链接 2,合作机构

	/**
	 * 读取名称
	 * 
	 * @return
	 * @see #name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * 
	 * @param name
	 * @see #name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 读取链接
	 * 
	 * @return
	 * @see #link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * 设置链接
	 * 
	 * @param link
	 * @see #link
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * 读取图片
	 * 
	 * @return
	 * @see #image
	 */
	@Lob
	public byte[] getImage() {
		return image;
	}

	/**
	 * 设置图片
	 * 
	 * @param image
	 * @see #image
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

	/**
	 * 读取顺序
	 * 
	 * @return
	 * @see #order
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * 设置顺序
	 * 
	 * @param order
	 * @see #order
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
