package kr.ac.hansung.cse.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//ctrl+shift+o 가 자동 import
//자동 정렬은 ctrl+shift+f

@Getter
@Setter
@ToString
public class Product {

	private int id;
	
	@NotEmpty(message="The Product name must not be null")
	private String name;
	
	private String category;
	
	@Min(value=0, message="The Product must not be less than zero ")
	private int price;
	
	@NotEmpty(message="The Product manufacturer must not be null")
	private String manufacturer;
	
	@Min(value=0, message="The Product unitInStock must not be less than zero ")
	private int unitInStock;
	
	private String description;
}
