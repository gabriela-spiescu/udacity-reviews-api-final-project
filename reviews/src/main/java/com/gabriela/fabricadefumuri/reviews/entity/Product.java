package com.gabriela.fabricadefumuri.reviews.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Gabriela Spiescu
 */
@Entity
@Table
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private String model;
	
	private String description;
	
	private String manufacturer;
	
	private Double price;
	
	private Boolean availability;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	@DBRef
	private List<Review> reviews = new ArrayList<Review>();
	
	@Transient
	private List<ReviewDocument> reviewsMongo = new ArrayList<ReviewDocument>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getAvailability() {
		return availability;
	}

	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<ReviewDocument> getReviewsMongo() {
		return reviewsMongo;
	}

	public void setReviewsMongo(List<ReviewDocument> reviewsMongo) {
		this.reviewsMongo = reviewsMongo;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", model=" + model + ", description=" + description + ", manufacturer=" + manufacturer + ", price=" + price + ", availability=" + availability
				+ ", reviews=" + reviews + ", reviewsMongo=" + reviewsMongo + "]";
	}
}
