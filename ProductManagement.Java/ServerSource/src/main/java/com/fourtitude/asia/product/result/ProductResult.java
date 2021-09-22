package com.fourtitude.asia.product.result;

import com.fourtitude.asia.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class ProductResult {

	private Long id;
	private String code;
	private String name;
	private String category;
	private String brand;
	private String type;
	private String description;
	
	public ProductResult(Product product) {
		
		this.id = product.getId();
        this.code = product.getCode();
        this.name = product.getName();
        this.category = product.getCategory();
        this.brand = product.getBrand();
        this.type = product.getType();
        this.description = product.getDescription();
	}	
}
