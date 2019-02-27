package com.vincentstarck.company.geospatialvehicle.service;

import java.util.List;

import com.vincentstarck.company.geospatialvehicle.model.Geolocation;
import com.vincentstarck.company.geospatialvehicle.model.Vehicle;

/**
 * @author vincent
 * Define the contract associated to the VehicleService class
 */
public interface IVehicleService {

	/**
	 * get the Vehicle associated to the placa given
	 * @param placa
	 * @return Vehicle
	 */
	Vehicle getVehicleByPlaca(String placa);
  
	/**
	 * Get the current Vehicle position
	 * @param placa
	 * @return geolocation object
	 */
	Geolocation getCurrentPosition(String placa);
	
	/**
	 * Add a new vehicle
	 * @param vehicle
	 * @return the Vehicle just added
	 */
	Vehicle addVehicle(Vehicle vehicle);
	
	/**
	 * Get the total of Vehicles
	 * @return list of Vehicles
	 */
	List<Vehicle> getAllVehicles();
	
	/**
	 * Update the vehicle position
	 * @param placa
	 * @param coordiante
	 * @return boolean
	 */
	boolean updatePosition(String placa, double[] coordiante);

}
