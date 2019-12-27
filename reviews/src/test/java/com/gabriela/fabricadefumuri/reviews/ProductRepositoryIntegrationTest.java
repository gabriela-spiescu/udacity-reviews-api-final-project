package com.gabriela.fabricadefumuri.reviews;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.gabriela.fabricadefumuri.reviews.entity.Product;
import com.gabriela.fabricadefumuri.reviews.repository.ProductRepository;


/**
 * @author Gabriela Spiescu
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryIntegrationTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private ProductRepository productRepository;
	
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
	public void saveNewProduct() {
		Product p = getProduct();
		entityManager.persist(p);
		entityManager.flush();
		
		Product found = productRepository.findByModel("NICE").get();
		assertThat(found.getModel()).isEqualTo(p.getModel());
	}
	
	@Test
	public void deleteProduct() {
		Product p1 = getProduct();
		Product p2 = getProduct();
		entityManager.persist(p1);
		entityManager.persist(p2);
		entityManager.flush();
		entityManager.clear();
		
		p1 = entityManager.find(Product.class, p1.getId());
		p2 = entityManager.find(Product.class, p2.getId());
		assertThat(entityManager.find(Product.class, p1.getId())).isNotNull();
		entityManager.remove(p1);
		entityManager.flush();
		entityManager.clear();
		assertThat(entityManager.find(Product.class, p1.getId())).isNull();
	}
	
}
