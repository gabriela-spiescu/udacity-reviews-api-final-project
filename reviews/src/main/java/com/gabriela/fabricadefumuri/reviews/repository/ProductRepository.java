package com.gabriela.fabricadefumuri.reviews.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabriela.fabricadefumuri.reviews.entity.Product;

/**
 * @author Gabriela Spiescu
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	Optional<Product> findByModel(String model);

}
