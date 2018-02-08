package com.jlfex.hermes.service.pojo;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ContentCategory {
	private String id;
	private String inputName;
	private String categoryLevelOne;
	private String categoryLevelTwo;
	private String code;

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public String getCategoryLevelOne() {
		return categoryLevelOne;
	}

	public void setCategoryLevelOne(String categoryLevelOne) {
		this.categoryLevelOne = categoryLevelOne;
	}

	public String getCategoryLevelTwo() {
		return categoryLevelTwo;
	}

	public void setCategoryLevelTwo(String categoryLevelTwo) {
		this.categoryLevelTwo = categoryLevelTwo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
