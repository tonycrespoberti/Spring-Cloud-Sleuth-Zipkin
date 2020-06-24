package com.cloud.netflix.item.service;

import java.util.List;

import com.cloud.netflix.item.model.Item;
import com.cloud.netflix.item.model.Producto;


public interface ItemService {

	public List<Item> findAll();
	
	public Item findById(Long id, Integer cantidad);
	
	public Producto save(Producto producto);
	
	public Producto update(Producto producto, Long idProduct);
	
	void delete(Long idProduct);
	
}
