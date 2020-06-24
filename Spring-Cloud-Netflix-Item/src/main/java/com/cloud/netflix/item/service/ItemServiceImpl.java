package com.cloud.netflix.item.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cloud.netflix.item.model.Item;
import com.cloud.netflix.item.model.Producto;

@Service("serviceRestTemplate") //We've assigned a name to become unique. Remember that we have two classes implementing the same interface. Spring needs to know which one to uses.
public class ItemServiceImpl implements ItemService{

	@Autowired
	private RestTemplate clienteRestTemplate; //Cliente HTTP
	
	@Override
	public List<Item> findAll() {
		
		//We change the following line to uncouple URL and Port for a Rest Client
		//List<Producto> productos = Arrays.asList(clienteRestTemplate.getForObject("http://192.168.1.135:8001/list/products", Producto[].class));
		List<Producto> productos = Arrays.asList(clienteRestTemplate.getForObject("http://servicio-productos/list/products", Producto[].class));
		
		//We have to convert productos in a List. Using stream we change each array producto element for an Item Object and then in a List.
		return productos.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		
		Map<String, String> pathVariables = new HashMap<String, String>();
		
		pathVariables.put("id", id.toString());
		
		//We change the following line to uncouple URL and Port for a Rest Client
		//Producto producto = clienteRestTemplate.getForObject("http://192.168.1.135:8001/view/{id}", Producto.class, pathVariables);
		Producto producto = clienteRestTemplate.getForObject("http://servicio-productos/view/{id}", Producto.class, pathVariables);
	
		return new Item(producto, cantidad);
	}

	//Create new Product
	@Override
	public Producto save(Producto producto) {
		
		HttpEntity<Producto> body = new HttpEntity<Producto>(producto);
		
		ResponseEntity<Producto> response = clienteRestTemplate.exchange("http://servicio-productos/create", HttpMethod.POST, body, Producto.class);
		
		Producto productoResponse = response.getBody();
		
		return productoResponse;
	}

	//Update a new Product
	@Override
	public Producto update(Producto producto, Long idProduct) {
		
		Map<String, String> pathVariables = new HashMap<String, String>();
		
		pathVariables.put("idProduct", idProduct.toString());
		
		HttpEntity<Producto> body = new HttpEntity<Producto>(producto);
		
		ResponseEntity<Producto> response = clienteRestTemplate.exchange("http://servicio-productos/edit/{id}", 
				HttpMethod.PUT, body, Producto.class, pathVariables);
		
		return response.getBody();
	}

	@Override
	public void delete(Long idProduct) {
		
		Map<String, String> pathVariables = new HashMap<String, String>();
		
		pathVariables.put("idProduct", idProduct.toString());
		
		clienteRestTemplate.delete("http://servicio-productos/delete/{idProduct}", pathVariables);
	}

}
