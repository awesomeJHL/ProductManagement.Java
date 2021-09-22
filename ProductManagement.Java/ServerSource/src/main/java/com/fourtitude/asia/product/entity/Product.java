package com.fourtitude.asia.product.entity;

import com.fourtitude.asia.product.model.AddProductParam;
import com.fourtitude.asia.product.model.EditProductParam;

import lombok.*;
import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Setter
@Getter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(length = 9)
    private String code;
    @Column(length = 90)
    private String name;
    @Column(length = 28)
    private String category;
    @Column(length = 28)
    private String brand;
    @Column(length = 21)
    private String type;
    @Column(length = 300)
    private String description;
    
	public Product(AddProductParam param) {
		
        this.code = param.getCode();
        this.name = param.getName();
        this.category = param.getCategory();
        this.brand = param.getBrand();
        this.type = param.getType();
        this.description = param.getDescription();
	}
	
	public Product(EditProductParam param) {
		
        this.name = param.getName();
        this.category = param.getCategory();
        this.brand = param.getBrand();
        this.type = param.getType();
        this.description = param.getDescription();
	}
}
