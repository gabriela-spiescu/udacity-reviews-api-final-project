package com.gabriela.fabricadefumuri.reviews.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.gabriela.fabricadefumuri.reviews.entity.CommentDocument;


/**
 * @author Gabriela Spiescu
 */
@Repository
public interface CommentMongoRepository extends MongoRepository<CommentDocument, String> {
	
	Optional<CommentDocument> findByReviewId(int reviewId);
	Optional<CommentDocument> findByTitle(String title);
}
