package com.gabriela.fabricadefumuri.reviews;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriela.fabricadefumuri.reviews.entity.Product;
import com.gabriela.fabricadefumuri.reviews.entity.Review;
import com.gabriela.fabricadefumuri.reviews.entity.ReviewDocument;
import com.gabriela.fabricadefumuri.reviews.repository.ProductRepository;
import com.gabriela.fabricadefumuri.reviews.repository.ReviewMongoRepository;
import com.gabriela.fabricadefumuri.reviews.repository.ReviewRepository;

/**
 * @author Gabriela Spiescu
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ReviewControllerTest {
	
	@MockBean
	private ReviewRepository reviewRepository;
	
	@MockBean
	private ReviewMongoRepository reviewMongoRepository;
	
	@MockBean
	private ProductRepository productRepository;
	
	@Autowired
	private MockMvc mvc;
	    
	@Autowired
	private JacksonTester<Review> json;
	
	public Review getReview(int id) {
		Review r = new Review();
		r.setId(id);
		r.setAuthor("Booby");
		r.setScore(4);
		r.setTitle("Lovely thing");
		return r;
	  }	
	
	public ReviewDocument getReviewDocument(int id) {
		ReviewDocument r = new ReviewDocument();
		r.setId(String.valueOf(id));
		r.setAuthor("Booby");
		r.setScore(4);
		r.setTitle("Lovely thing");
		return r;
	  }
	
	@Test
	public void listReviewsForProduct() throws Exception {
//		List<Review> reviews = new ArrayList<Review>();
//		reviews.add(getReview(1));
//		reviews.add(getReview(2));
//		reviews.add(getReview(3));
//		Product product = new Product();
//		product.setId(1);
//		product.setReviews(reviews);
//		Optional<Product> opt = Optional.of(product);
//		Mockito.when(productRepository.findById(product.getId())).thenReturn(opt);
//		Mockito.when(reviewRepository.findAllByProduct(String.valueOf(product.getId()))).thenReturn(reviews);
//		mvc.perform(get("/reviews/products/" + product.getId())
//		    	 .contentType(MediaType.APPLICATION_JSON_UTF8)
//		    	 .accept(MediaType.APPLICATION_JSON_UTF8)
//		.content(convertObjectToJsonBytes(reviews)))
//		         .andExpect(status().isOk())
//		         .andExpect(jsonPath("$.[0].id", is(1)))
//		         .andExpect(jsonPath("$.[1].id", is(2)))
//		         .andExpect(jsonPath("$.[0].author", is("Booby")))
//		         .andExpect(jsonPath("$", hasSize(3)));

		List<ReviewDocument> reviews2 = new ArrayList<ReviewDocument>();
		reviews2.add(getReviewDocument(1));
		reviews2.add(getReviewDocument(2));
		reviews2.add(getReviewDocument(3));
		Product product = new Product();
		product.setId(1);
		Optional<Product> opt = Optional.of(product);
		product.setReviewsMongo(reviews2);
		Mockito.when(productRepository.findById(product.getId())).thenReturn(opt);
		Mockito.when(reviewMongoRepository.findAllByProductid(product.getId())).thenReturn(reviews2);
		mvc.perform(get("/reviews/products/" + product.getId())
		    	 .contentType(MediaType.APPLICATION_JSON_UTF8)
		    	 .accept(MediaType.APPLICATION_JSON_UTF8)
		.content(convertObjectToJsonBytes(reviews2)))
		         .andExpect(status().isOk())
		         .andExpect(jsonPath("$.[0].id", is("1")))
		         .andExpect(jsonPath("$.[1].id", is("2")))
		         .andExpect(jsonPath("$.[0].author", is("Booby")))
		         .andExpect(jsonPath("$", hasSize(3)));
	}
	
	
	@Test
	public void createReviewForProduct() throws URISyntaxException, IOException, Exception {
		Product product = new Product();
		product.setId(1);
		Review r = getReview(1);
		r.setProduct(product);
		Optional<Product> opt = Optional.of(product);
		Mockito.when(productRepository.findById(product.getId())).thenReturn(opt);
		mvc.perform(post(new URI("/reviews/products/" + product.getId()))
				  .content(json.write(r).getJson())
				  .contentType(MediaType.APPLICATION_JSON_UTF8)
				  .accept(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isCreated());
		
	}
	
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(object);
    }

}
