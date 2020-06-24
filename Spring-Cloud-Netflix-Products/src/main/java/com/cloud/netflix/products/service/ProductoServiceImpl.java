package com.cloud.netflix.products.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.netflix.products.model.entity.Producto;
import com.cloud.netflix.products.model.repo.ProductoRepo;

@Service //This is a stereotype of Component
public class ProductoServiceImpl implements IProductoService{

	//Layer Dao injected
	@Autowired
	private ProductoRepo productoRepo;
	
	//****
	

/*
	//FIND BY name products by a pattern
	@Override
	public List<Producto> findByName(String patternName) {
		
		return productoRepo.findByName(patternName);
	}
*/
	//FIND ALL
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {

		return productoRepo.findAll();
	}

	//SAVE 
	@Override
    @Transactional
	public Producto save(Producto producto) {

		return productoRepo.save(producto);
	}

	//DELETE
	@Override
	@Transactional
	public void deleteById(Long id) {

		productoRepo.deleteById(id);
		
	}

	
	//FIND BY ID
	@Override
	@Transactional(readOnly = true)
	public Producto findById(Long id) {

		return productoRepo.findById(id).orElse(null); // If it is found return the product else return null
	}
}
