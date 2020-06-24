package com.cloud.netflix.item.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.netflix.item.model.Item;
import com.cloud.netflix.item.model.Producto;
import com.cloud.netflix.item.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@RefreshScope //Allow to update, App Properties, Components, Controllers, classes (@Component, @Service) 
//injected with Value Configuration in real time throught Actuator dependecy
@RestController
public class ItemController {

	private static Logger log = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	@Qualifier("serviceFeign") //Specifying a name, Spring knows which one to uses, because we have two classes that implement the same interface.
	//@Qualifier("serviceRestTemplate")
	private ItemService itemService;
	
	//It is injected a property value defined in Server Config que provides that value
	@Value("${configuracion.texto}")
	private String texto;
	
	//********
	
	@GetMapping(path = "/list/products")
	public List<Item> list(){
		
		return itemService.findAll();
	}
	
	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@GetMapping(path = "/view/{id}/cantidad/{cantidad}")
	public Item detail(@PathVariable Long id, @PathVariable Integer cantidad) {
		
		return itemService.findById(id, cantidad);
	}
	
	//Only to test Hystrix providing an alternative method for fault tolerance
	public Item metodoAlternativo(Long id, Integer cantidad) {
		
		Item item = new Item();
		Producto producto = new Producto();
		
		item.setCantidad(cantidad);
		producto.setName("Default - Móvil Oppo");
		producto.setPrice(500.00);
		item.setProducto(producto);
		
		return item;
		
	}
	
	@GetMapping("/obtain-config")
	public ResponseEntity<?> obtainConfiguration(@Value("${server.port}") String puerto){
		
		Map<String, String> json = new HashMap<>();
		json.put("texto", texto);
		json.put("port", puerto);
		
		log.info("El texto: " + texto);
		log.info("El puerto: " + puerto);
		
		if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
			
			json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
			json.put("autor.email", env.getProperty("configuracion.autor.email"));
			
		}
		
		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto createProduct(@RequestBody Producto producto) { 
		
		return itemService.save(producto);
	}
	
	
	@PutMapping("edit/{idProduct")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Producto updateProduct(@RequestBody Producto producto) {		 

		return itemService.save(producto);
		
	}
	
	@DeleteMapping("/delete/{idProduct}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProduct(@PathVariable Long idProduct) {
	
		itemService.delete(idProduct);
		
	}
}
