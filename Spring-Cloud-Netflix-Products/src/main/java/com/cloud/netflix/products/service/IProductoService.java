package com.cloud.netflix.products.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.cloud.netflix.products.model.entity.Producto;

public interface IProductoService {
	//@Query(value = "SELECT p FROM productos p WHERE p.PRODUCT_NAME LIKE %?1%")
	//List<Producto> findByName(String name);
	
	List<Producto> findAll();
	
	Producto save(Producto producto);
	
	void deleteById(Long id);
	
	Producto findById(Long id);
}
