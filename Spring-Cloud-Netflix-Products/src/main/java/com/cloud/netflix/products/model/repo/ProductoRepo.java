package com.cloud.netflix.products.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloud.netflix.products.model.entity.Producto;

public interface ProductoRepo extends JpaRepository<Producto, Long>{

	
}
