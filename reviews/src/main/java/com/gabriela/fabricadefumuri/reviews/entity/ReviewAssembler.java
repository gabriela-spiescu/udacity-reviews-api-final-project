package com.gabriela.fabricadefumuri.reviews.entity;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import javax.persistence.EntityExistsException;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.gabriela.fabricadefumuri.reviews.controller.ReviewsController;

/**
 * @author Gabriela Spiescu
 */
@Component
public class ReviewAssembler implements ResourceAssembler<Review, Resource<Review>>{

	/**
	 * @param entity
	 * @return
	 */
	@Override
	public Resource<Review> toResource(Review entity) {
		Resource<Review> reviewResource = new Resource<Review>(entity);
		    try {
		        Link selfLink = linkTo(ReviewsController.class).slash("reviews").slash(String.valueOf(entity.getId())).withSelfRel();
		        reviewResource.add(selfLink);
		    } catch (EntityExistsException e) {
		        e.printStackTrace();
		    }
		    return reviewResource;
	}

}
