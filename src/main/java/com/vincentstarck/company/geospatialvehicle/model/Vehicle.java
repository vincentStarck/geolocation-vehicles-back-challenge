package com.vincentstarck.company.geospatialvehicle.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Vehicle implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * placa of car , used as a primary key 
	 */
	@Id
	@NotNull
    @Size(min=3, max=10)
	@NotEmpty
	private String placa;
	/**
	 * brand of the vehicle
	 */
	@NotNull
    @Size(min=2, max=15)
	@NotEmpty
	private String marca;
	/**
	 * geolocation data
	 */
	private Geolocation geolocation;




}
