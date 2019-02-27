package com.vincentstarck.company.geospatialvehicle.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author vincent
 *Classe used to model the response to the API´s clients
 */
@Builder
@AllArgsConstructor
@Data
public class ResponseBase {

	
	public ResponseBase() {
		
	
	}
	/**
	 * flag to indicate the operation result
	 */
	private boolean success;
	
	/**
	 * Message regarding to the operation
	 */
	private String message;
	
	/**
	 * vehicle data
	 */
	private Vehicle vehicle;
	
	/**
	 * Geolocation data
	 */
	private Geolocation geolocation;
    
	/**
	 *Vehicles list used when the client wants to know the total of vehicles  
	 */
	private List<Vehicle> vehicles;

	



}
