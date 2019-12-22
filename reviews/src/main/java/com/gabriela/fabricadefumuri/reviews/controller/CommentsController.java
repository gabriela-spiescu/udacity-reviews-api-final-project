package com.gabriela.fabricadefumuri.reviews.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import com.gabriela.fabricadefumuri.reviews.entity.Comment;
import com.gabriela.fabricadefumuri.reviews.entity.CommentDocument;
import com.gabriela.fabricadefumuri.reviews.entity.Review;
import com.gabriela.fabricadefumuri.reviews.entity.ReviewDocument;
import com.gabriela.fabricadefumuri.reviews.repository.CommentMongoRepository;
import com.gabriela.fabricadefumuri.reviews.repository.CommentRepository;
import com.gabriela.fabricadefumuri.reviews.repository.ReviewMongoRepository;
import com.gabriela.fabricadefumuri.reviews.repository.ReviewRepository;

/**
 * @author Gabriela Spiescu
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired 
	private ReviewMongoRepository reviewMongoRepository;
    @Autowired 
    private CommentMongoRepository commentMongoRepository;
	
    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<Comment> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @RequestBody Comment comment, UriComponentsBuilder ucb) {
    	Optional<Review> review = reviewRepository.findById(reviewId);
    	if (review.isPresent()) {
    		comment.setReview(review.get());
    	  	List<Comment> comments = review.get().getComments();
    		comments.add(comment);
    		commentRepository.save(comment);
    		createCommentForReviewMongo(reviewId, comment);
        	return ResponseEntity.ok(comment);
    	} else {
    		return ResponseEntity.notFound().build();
    	}
    }
    
    private void createCommentForReviewMongo(Integer reviewId,Comment comment) {
    	Optional<ReviewDocument> optional = reviewMongoRepository.findById(String.valueOf(reviewId));
        CommentDocument commentMongo = new CommentDocument();
    	if (optional.isPresent()) {
    		commentMongo.setId(String.valueOf(comment.getId()));
    		commentMongo.setBody(comment.getBody());
    		commentMongo.setTitle(comment.getTitle());
    		commentMongo.setIsPositiv(comment.getIsPositiv());
    		commentMongo.setReviewId(reviewId);
            ReviewDocument review = optional.get();
            List<CommentDocument> comments = review.getCommentsMongo();
            comments.add(commentMongo);
            review.setCommentsMongo(comments);
            reviewMongoRepository.save(review);
            commentMongoRepository.save(commentMongo);
        }
    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public List<?> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
    	Optional<Review> review = reviewRepository.findById(reviewId);
    	Optional<ReviewDocument> rdo =  reviewMongoRepository.findById(String.valueOf(reviewId));
    	if (rdo.isPresent()) {
    		return rdo.get().getCommentsMongo();
    	} 
    	if (review.isPresent()) {
    		return review.get().getComments();
    	} else {
    		throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
    	}
    }
}