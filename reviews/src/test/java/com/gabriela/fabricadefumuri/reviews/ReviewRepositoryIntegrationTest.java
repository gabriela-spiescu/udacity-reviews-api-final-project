package com.gabriela.fabricadefumuri.reviews;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.gabriela.fabricadefumuri.reviews.entity.Product;
import com.gabriela.fabricadefumuri.reviews.entity.Review;
import com.gabriela.fabricadefumuri.reviews.repository.ReviewRepository;

/**
 * @author Gabriela Spiescu
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewRepositoryIntegrationTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	public Review getReview() {
		Review r = new Review();
		r.setAuthor("Booby");
		r.setScore(4);
		r.setTitle("Lovely thing");
		r.setCreatedTime(new Timestamp(100000));
		return r;
	 }	
	
	public Product getProduct() {
		Product p = new Product();
		p.setName("TRF");
		p.setModel("NICE");
		p.setManufacturer("MYNE");
		p.setPrice(123.6);
		p.setAvailability(false);
		p.setDescription("Nice photos");
		p.setReviews(null);
		return p;
	}
	
	@Test
	public void saveNewReview() {
		Product p = getProduct();
		entityManager.persist(p);
		Review r = getReview();
		r.setProduct(p);
		entityManager.persist(r);
		entityManager.flush();
		
		Review found = reviewRepository.findByTitle("Lovely thing").get();
		assertThat(found.getScore()).isEqualTo(r.getScore());
	}
	
	@Test
	public void deleteReview() {
		Product p = getProduct();
		entityManager.persist(p);
		
		Review r1 = getReview();
		Review r2 = getReview();
		r1.setProduct(p);
		r2.setProduct(p);
		entityManager.persist(r1);
		entityManager.persist(r2);
		entityManager.flush();
		entityManager.clear();
		
		r1 = entityManager.find(Review.class, r1.getId());
		r2 = entityManager.find(Review.class, r2.getId());
		assertThat(entityManager.find(Review.class, r2.getId())).isNotNull();
		entityManager.remove(r2);
		entityManager.flush();
		entityManager.clear();
		assertThat(entityManager.find(Review.class, r2.getId())).isNull();
	}
	
}
