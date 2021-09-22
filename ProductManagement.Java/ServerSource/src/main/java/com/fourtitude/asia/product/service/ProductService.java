package com.fourtitude.asia.product.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourtitude.asia.product.entity.Product;
import com.fourtitude.asia.product.model.AddProductParam;
import com.fourtitude.asia.product.model.EditProductParam;
import com.fourtitude.asia.product.repository.ProductRepository;
import com.fourtitude.asia.product.result.ProductResult;
import com.vaadin.flow.component.page.Page;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
   
    public List<ProductResult> getProductList(Pageable pageable) throws Exception {
        return productRepository.findAll(pageable)
                .get()
                .map(ProductResult::new)
                .collect(Collectors.toList());
    } 
    
    @Transactional
    public ProductResult addBoard(AddProductParam param) throws Exception {
        Product entity = new Product(param);
        return new ProductResult(productRepository.save(entity));
    }    
    
    @Transactional
    public ProductResult editProduct(EditProductParam param, Long seq) throws Exception {
    	Product product = getProductOrElseThrow(seq);
    	
    	//model set to entity model
        if(product != null) {
        	product.setName(param.getName());
        	product.setCategory(param.getCategory());
        	product.setBrand(param.getBrand());
        	product.setType(param.getType());
        	product.setDescription(param.getDescription());
        }
        
        return new ProductResult(product);
    }
    
    @Transactional
    public boolean deleteProduct(Long seq) throws Exception {
    	Product product = getProductOrElseThrow(seq);
    	
    	//model set to entity model
        if(product == null) {
        	
        }
        productRepository.delete(product);        
        return true;
    }    

    
    public ProductResult getProduct(Long id) throws Exception {
        return new ProductResult(getProductOrElseThrow(id));
    }    
    
    private Product getProductOrElseThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Product."));
    }    
}