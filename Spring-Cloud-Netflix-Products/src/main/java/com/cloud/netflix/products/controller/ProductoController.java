package com.cloud.netflix.products.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.netflix.products.model.entity.Producto;
import com.cloud.netflix.products.service.IProductoService;

@RestController //Each handler mapped to url path will be return in json format
public class ProductoController {

	@Autowired
	IProductoService productoService;
	
	//Class Enviroment from package org.springframework.core.env
	//When we execute two clients to consume the API we identify via Enviroment var who is who
	//Enviroment provides us application-properties read capacity to get its an specifica content. In this case, the port of servicio-productos
	//Will be used for Feign Client
	@Autowired
	private Environment env; 
	
	//From properties file, we read the random port assigned. In this way we can identify who is who consuming resources
	//Will be used for Rest Client
	@Value("${server.port}")
	private Integer port;
	
	//********
	
	//Another way to define the EndPoitn @RequestMapping(value = "/list", method = RequestMethod.GET)
	@GetMapping(path = "/list/products")
	public List<Producto> listProducts(){
		
		return productoService.findAll().stream().map(producto -> {
			//producto.setPort(Integer.parseInt(env.getProperty("local.server.port"))); Used for Feign Client
			producto.setPort(port); //Used for Rest Client
			return producto;
		}).collect(Collectors.toList());
	}
	
	//Find By Id
	@GetMapping(path = "/view/{id}")
	public Producto detail(@PathVariable Long id) throws Exception {
		
		Producto producto = productoService.findById(id);
		//producto.setPort(Integer.parseInt(env.getProperty("local.server.port"))); Used for Feign Client
		producto.setPort(port); //Used for Rest Client
		
		//Disable this lines if you want to simulate an error to test Hystrix
		/*
		boolean ok = false;
		if (ok == false) {
			throw new Exception("No se puede cargar el producto, se ha producido un fallo");
		}
		*/
		
		/*		
		//This code is to simulate an important delay to introduce Hystrix. 
		//This timeout 2 secs is to going up 1secs that is time default in Ribbon and Hystrix will be launched an exception.
		try {
			Thread.sleep(2000L);
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		*/	
		
		return productoService.findById(id);
	}
	
	@PostMapping(path = "/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto createProduct(@RequestBody Producto producto) {
		
		return productoService.save(producto);
	}
	
	@PutMapping(path = "/edit/{idProduct}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editProduct(@RequestBody Producto producto, @PathVariable Long idProduct) {
		
		Producto productoDB = productoService.findById(idProduct);
		productoDB.setName(producto.getName());
		productoDB.setPrice(producto.getPrice());
		 
		return productoService.save(productoDB);
		
	}
	
	@DeleteMapping(path = "/delete/{idProduct}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProduct(@PathVariable Long idProduct){
		
		productoService.deleteById(idProduct);
	}
	
}
