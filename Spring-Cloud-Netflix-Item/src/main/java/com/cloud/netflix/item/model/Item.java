package com.cloud.netflix.item.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//This class will be used to obtain an Item Producto using its quantity * its price
public class Item implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Producto producto;
	
	private Integer cantidad;
	
	//********
	
	public Double getTotal() {
		
		return producto.getPrice() * cantidad.doubleValue();
		
	}
	
}
