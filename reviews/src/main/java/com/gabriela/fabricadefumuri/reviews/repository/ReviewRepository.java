package com.gabriela.fabricadefumuri.reviews.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabriela.fabricadefumuri.reviews.entity.Review;

/**
 * @author Gabriela Spiescu
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

	Optional<Review> findByTitle(String string);
	List<Review> findAllByProduct(String productId);

}
