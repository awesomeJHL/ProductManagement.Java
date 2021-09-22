package com.fourtitude.asia.product.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.fourtitude.asia.product.model.AddProductParam;
import com.fourtitude.asia.product.model.EditProductParam;
import com.fourtitude.asia.product.result.ProductResult;
import com.fourtitude.asia.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ProductController {
	
    private final ProductService productService;
    private static final int PAGE_SIZE = 10;
    
    /**
     * Get Product List
     * @return ResponseEntity
     * @throws Exception
     */
    @CrossOrigin("*")    
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity getProductList(@PageableDefault(page = 0, size = PAGE_SIZE, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) throws Exception {
        List<ProductResult> productList = productService.getProductList(pageable);
        
        CollectionModel entityModel = CollectionModel.of(productList);
        
        return ResponseEntity.ok(entityModel);
    }
    
    /**
     * Get Product Detail
     * @return ResponseEntity
     * @throws Exception
     */    
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity getProduct(@PathVariable("id") Long id) throws Exception {
        ProductResult productResult = productService.getProduct(id);
        if (productResult == null) {
            return ResponseEntity.notFound().build();
        } else {
            EntityModel entityModel = EntityModel.of(productResult);
            return ResponseEntity.ok(entityModel);
        }
    } 
    
    /**
     * Product Create
     * @return ResponseEntity
     * @throws Exception
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity addProduct(@RequestBody @Validated AddProductParam param) throws Exception {
    	ProductResult productResult = productService.addBoard(param);
        return ResponseEntity.ok(productResult);
    }
    
    /**
     * Product Update
     * @return ResponseEntity
     * @throws Exception
     */   
    @CrossOrigin(origins = "*", allowedHeaders = "*")    
    @PatchMapping(value = "/{id}", produces = "application/json", consumes = "application/json")	
    public ResponseEntity editProduct(@PathVariable("id") Long id, @RequestBody @Validated EditProductParam param) throws Exception {
        ProductResult productResult = productService.editProduct(param, id);
        if (productResult == null) {
            return ResponseEntity.notFound().build();
        }
        EntityModel<ProductResult> entityModel = EntityModel.of(productResult);
        return ResponseEntity.ok(entityModel);
    }
    
    /**
     * Product Delete
     * @return ResponseEntity
     * @throws Exception
     */      
    @CrossOrigin(origins = "*", allowedHeaders = "*")    
    @DeleteMapping(value = "/{id}", produces = "application/json", consumes = "application/json")	
    public ResponseEntity deleteProduct(@PathVariable("id") Long id) throws Exception {
        if (productService.deleteProduct(id)) {
            Map<String, Long> resultMap = new HashMap<>();
            resultMap.put("deletedId", id);
            EntityModel entityModel = EntityModel.of(resultMap, getLinkAddress().slash(id).withSelfRel());        	
            return ResponseEntity.ok(entityModel);
        }
        return ResponseEntity.notFound().build();
    }    
    
    private WebMvcLinkBuilder getLinkAddress() {
        return linkTo(ProductController.class);
    }
}
