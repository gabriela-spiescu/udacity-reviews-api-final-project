package com.gabriela.fabricadefumuri.reviews.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.gabriela.fabricadefumuri.reviews.entity.Product;
import com.gabriela.fabricadefumuri.reviews.entity.Review;
import com.gabriela.fabricadefumuri.reviews.entity.ReviewAssembler;
import com.gabriela.fabricadefumuri.reviews.entity.ReviewDocument;
import com.gabriela.fabricadefumuri.reviews.repository.ProductRepository;
import com.gabriela.fabricadefumuri.reviews.repository.ReviewMongoRepository;
import com.gabriela.fabricadefumuri.reviews.repository.ReviewRepository;


/**
 * @author Gabriela Spiescu
 */
@RestController
public class ReviewsController {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private ReviewMongoRepository reviewMongoRepository;
	
	@Autowired
	private ProductRepository productRepository;
    
	private final ReviewAssembler reviewAssembler;

	ReviewsController(ReviewAssembler reviewAssembler) {
		this.reviewAssembler = reviewAssembler;	
	}
	
	/**
     * @return list of all reviews
     */
    @GetMapping
    public ResponseEntity<List<Resource<Review>>>  list() {
    	List<Resource<Review>> reviews = new ArrayList<Resource<Review>>();
    	for (Review r: reviewRepository.findAll()) {
    		Resource<Review> rResource = reviewAssembler.toResource(r);
    		reviews.add(rResource);
    	}
    	return new ResponseEntity<List<Resource<Review>>>(reviews, HttpStatus.OK);
    }
	
    /**
     * Creates a review for a product.
     * 
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Integer productId, @RequestBody Review review) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
        	Product product = productOpt.get();
        	review.setProduct(product);
        	reviewRepository.save(review);
        	ReviewDocument reviewMongo = createReviewForProductMongo(productId, review);
        	List<ReviewDocument> reviewsMongoFromProduct = product.getReviewsMongo();
        	reviewsMongoFromProduct.add(reviewMongo);
        	product.setReviewsMongo(reviewsMongoFromProduct);
        	productRepository.save(product);
        	return new ResponseEntity<Review>(review, HttpStatus.CREATED);
        } else {
        	throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }
    }
    
    private ReviewDocument createReviewForProductMongo(Integer productId, Review review) {
    	ReviewDocument reviewMongo = new ReviewDocument();
    	reviewMongo.setAuthor(review.getAuthor());
    	reviewMongo.setScore(review.getScore());
    	reviewMongo.setTitle(review.getTitle());
    	reviewMongo.setCreatedTime(review.getCreatedTime());
    	reviewMongo.setId(String.valueOf(review.getId()));
    	reviewMongo.setProductid(productId);
    	reviewMongoRepository.save(reviewMongo);
    	return reviewMongo;
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
    	Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
//        	List<Review> reviewsFromProduct = product.get().getReviews();
//        	List<ReviewDocument> result = new ArrayList<ReviewDocument>();
//        	for (Review r: reviewsFromProduct) {
//        		result.add(reviewMongoRepository.findById(String.valueOf(r.getId())).get());
//        	}
        	List<ReviewDocument> reviewsFromProductMongo = reviewMongoRepository.findAllByProductid(productId);
        	return new ResponseEntity<List<?>>(reviewsFromProductMongo, HttpStatus.OK);
//        	return new ResponseEntity<List<?>>(reviewsFromProduct, HttpStatus.OK);
        } else {
        	throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }
    }
}