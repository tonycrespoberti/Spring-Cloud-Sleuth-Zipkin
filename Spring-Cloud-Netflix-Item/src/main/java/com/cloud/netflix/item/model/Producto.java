package com.cloud.netflix.item.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
//This is an object but not to persist it, just to populate it with data from API Producto
public class Producto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	
	private Double price;
	
	private Date createAt;
	
	private Integer port;
	
	//*******************
	
}
