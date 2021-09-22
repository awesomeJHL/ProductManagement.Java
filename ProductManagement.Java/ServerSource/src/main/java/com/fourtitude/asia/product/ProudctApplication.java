package com.fourtitude.asia.product;

import com.fourtitude.asia.product.entity.Product;
import com.fourtitude.asia.product.model.AddProductParam;
import com.fourtitude.asia.product.repository.ProductRepository;
import com.fourtitude.asia.product.utils.ProductStorage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProudctApplication {

	@Autowired
	private ProductRepository productRepository;	
	
	//application start point
	public static void main(String[] args) {
		SpringApplication.run(ProudctApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner() {
		return args -> {
			// data initialze
			ProductStorage storge = new ProductStorage();
			// get sample data
			ArrayList<AddProductParam> params = storge.loadDataset();
			// store to memorydatabase
			for (AddProductParam param : params) {
				productRepository.save(new Product(param));
			}
		};
	}
	
}
