package com.gabriela.fabricadefumuri.reviews.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabriela.fabricadefumuri.reviews.entity.Comment;
import com.gabriela.fabricadefumuri.reviews.entity.Review;

/**
 * @author Gabriela Spiescu
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	Optional<Comment> findByTitle(String title);
	List<Comment> findByReview(Review review);

}
