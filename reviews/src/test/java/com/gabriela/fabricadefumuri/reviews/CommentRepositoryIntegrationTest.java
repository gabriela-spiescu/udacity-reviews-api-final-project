package com.gabriela.fabricadefumuri.reviews;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.gabriela.fabricadefumuri.reviews.entity.Comment;
import com.gabriela.fabricadefumuri.reviews.entity.Product;
import com.gabriela.fabricadefumuri.reviews.entity.Review;
import com.gabriela.fabricadefumuri.reviews.repository.CommentRepository;


/**
 * @author Gabriela Spiescu
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryIntegrationTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private CommentRepository commentRepository;
	
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
	
	public Review getReview() {
		Review r = new Review();
		r.setAuthor("Booby");
		r.setScore(4);
		r.setTitle("Lovely thing");
		r.setCreatedTime(new Timestamp(100000));
		return r;
	 }	
	
	@Test
	public void saveNewComment() {
		Product p = getProduct();
		entityManager.persist(p);
		Review r = getReview();
		r.setProduct(p);
		entityManager.persist(r);
		Comment c = new Comment();
		c.setTitle("Diva");
		c.setBody("Opera");
		c.setIsPositiv(true);
		c.setReview(r);
		entityManager.persist(c);
		entityManager.flush();
		
		Comment found1 = commentRepository.findByTitle("Diva").get();
		assertThat(found1.getTitle()).isEqualTo(c.getTitle());
	}
	
	@Test
	public void deleteComment() {
		Product p = getProduct();
		entityManager.persist(p);
		Review r = getReview();
		r.setProduct(p);
		entityManager.persist(r);
		Comment c1 = new Comment();
		c1.setTitle("Diva");
		c1.setBody("Opera");
		c1.setIsPositiv(true);
		c1.setReview(r);
		entityManager.persist(c1);
		Comment c2 = new Comment();
		c2.setTitle("Diva");
		c2.setBody("Opera");
		c2.setIsPositiv(true);
		c2.setReview(r);
		entityManager.persist(c2);
		entityManager.flush();
		
		commentRepository.deleteById(c1.getId());
		commentRepository.flush();
		assertThat(commentRepository.findAll().size()).isEqualTo(1);
	}
	
	@Test
	public void findByReview() {
		Product p = getProduct();
		entityManager.persist(p);
		Review r = getReview();
		r.setProduct(p);
		entityManager.persist(r);
		Comment c = new Comment();
		c.setTitle("Diva");
		c.setBody("Opera");
		c.setIsPositiv(true);
		c.setReview(r);
		entityManager.persist(c);
		entityManager.flush();
		
		List<Comment> found1 = commentRepository.findByReview(r);
		assertThat(found1.size()).isEqualTo(1);
	}
	

}
