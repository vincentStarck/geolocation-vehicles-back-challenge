package com.vincentstarck.company.geospatialvehicle.respository;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vincentstarck.company.geospatialvehicle.model.Vehicle;


/**
 * @author vincent
 * Interface used to persist the vehicle data to MongoDB
 */
@Repository
public interface VehicleRepository extends MongoRepository<Vehicle,String> {
	
	
	public Optional<Vehicle> findByPlaca(String placa);

}
