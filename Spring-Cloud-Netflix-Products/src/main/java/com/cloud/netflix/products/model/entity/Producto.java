package com.cloud.netflix.products.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "productos")
@Getter //Lombok Project Annotation
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUCT_ID")
	private Long id;
	
	@Column(name = "PRODUCT_NAME")
	private String name;
	
	@Column(name = "PRICE")
	private Double price;
	
	@Column(name = "CREATE_AT")
	@Temporal(TemporalType.DATE) //This annotation together Date class defines the type of format date that we want to use.
	                             //In this case, only date sql format.
	private Date createAt;

	//Not persistency attribute, Not associated to any table or DDBB
	//It will be used to identify who is who, when API Rest is consuming any resource.
	@Transient
	private Integer port;
}
