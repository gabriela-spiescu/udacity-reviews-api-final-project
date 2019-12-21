package com.gabriela.fabricadefumuri.reviews.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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

@Document(collection = "reviewdocument")
public class ReviewDocument {
	
	@Id
	private String id;
	
	private String author;
	
	private String title;
	
	private Integer score;
	
	private Timestamp createdTime;
	
	private int productid;
	
	public int getProductid() {
		return productid;
	}

	public void setProductid(int productid) {
		this.productid = productid;
	}

	@DBRef
	@OneToMany(mappedBy = "reviewId", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<Comment>();
	
	private List<CommentDocument> commentsMongo = new ArrayList<CommentDocument>();

	public List<CommentDocument> getCommentsMongo() {
		return commentsMongo;
	}

	public void setCommentsMongo(List<CommentDocument> commentsMongo) {
		this.commentsMongo = commentsMongo;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public ReviewDocument() {
	}
	
}
