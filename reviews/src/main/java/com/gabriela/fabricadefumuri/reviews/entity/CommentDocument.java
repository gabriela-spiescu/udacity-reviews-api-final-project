package com.gabriela.fabricadefumuri.reviews.entity;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Gabriela Spiescu
 */
@Getter
@Setter
@ToString

@Document(collection = "commentdocument")
public class CommentDocument {
	
	@Id
	private String id;
	
	private String body;
	
	private String title;
	
	private Boolean isPositiv;
	
	private Integer reviewId;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getIsPositiv() {
		return isPositiv;
	}

	public void setIsPositiv(Boolean isPositiv) {
		this.isPositiv = isPositiv;
	}
	
}
