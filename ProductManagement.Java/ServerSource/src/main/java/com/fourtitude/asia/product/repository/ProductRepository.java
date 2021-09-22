package com.fourtitude.asia.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.fourtitude.asia.product.entity.Product;
@Component
public interface ProductRepository extends JpaRepository<Product, Long> {
}