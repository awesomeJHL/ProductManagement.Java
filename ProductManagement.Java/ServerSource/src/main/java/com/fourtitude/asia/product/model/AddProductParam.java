package com.fourtitude.asia.product.model;

import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AddProductParam {
	@NotEmpty(message = "Required.")
	private String code;
	@NotEmpty(message = "Required.")
	private String name;
	@NotEmpty(message = "Required.")	
	private String category;
	private String brand;
	private String type;
	@Length(max = 300, message = "최소 {min}자 이상 최대 {max}자 이하로 입력해주시기 바랍니다.")
	private String description;
}
