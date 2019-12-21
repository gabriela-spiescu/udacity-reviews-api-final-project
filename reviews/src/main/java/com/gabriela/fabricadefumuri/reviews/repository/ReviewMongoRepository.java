package com.gabriela.fabricadefumuri.reviews.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.gabriela.fabricadefumuri.reviews.entity.ReviewDocument;

/**
 * @author Gabriela Spiescu
 */
@Repository
public interface ReviewMongoRepository extends MongoRepository<ReviewDocument, String> {
	
	List<ReviewDocument> findAllByProductid(int productid);
	ReviewDocument findByTitle(String title);
}
