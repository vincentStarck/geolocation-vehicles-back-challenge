package com.vincentstarck.company.geospatialvehicle.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vincentstarck.company.geospatialvehicle.constant.OperationResult;
import com.vincentstarck.company.geospatialvehicle.model.Geolocation;
import com.vincentstarck.company.geospatialvehicle.model.ResponseBase;
import com.vincentstarck.company.geospatialvehicle.model.Vehicle;
import com.vincentstarck.company.geospatialvehicle.service.IVehicleService;

/**
 * @author vincent
 * Class that expose the API Rest method according to the vehicle service
 */
@RestController
@RequestMapping("vehicles")
public class VehicleController {

	@Autowired
	private IVehicleService vehicleService;

	/**
	 * Method that return all vehicles
	 * @return  ResponseBase with a list of vehicles
	 */
	@GetMapping("/")
	public ResponseEntity<ResponseBase> getAllVehicles() {

		List<Vehicle> vehicles = this.vehicleService.getAllVehicles();
		ResponseBase response = ResponseBase.builder().success(Boolean.TRUE)
				.message(OperationResult.SUCCESS.getMessage()).vehicles(vehicles).build();
		return new ResponseEntity<ResponseBase>(response, HttpStatus.OK);
	}

	/**
	 * Method que return a vehicle that correspond with the placa given
	 * @param placa
	 * @return ResponseBase with the vehicle 
	 */
	@GetMapping("/byPlaca")
	public ResponseEntity<ResponseBase> getVehicleByPlaca(
			@RequestParam(name = "placa") String placa) {

		Vehicle vehicle = vehicleService.getVehicleByPlaca(placa);
		ResponseBase response = ResponseBase.builder().success(Boolean.TRUE)
				.message(OperationResult.SUCCESS.getMessage()).vehicle(vehicle).build();
		return new ResponseEntity<ResponseBase>(response, HttpStatus.OK);

	}

	/**
	 * Method that return the current position of the vehicle associated to the placa given
	 * @param placa
	 * @return ResponseBase with the vehicle geolocation object
	 */
	@GetMapping("/position")
	public ResponseEntity<ResponseBase> getCurrentPosition(
			@RequestParam(name = "placa") String placa) {

		Geolocation geolocation = vehicleService.getCurrentPosition(placa);
		ResponseBase respose = ResponseBase.builder().success(Boolean.TRUE)
				.message(OperationResult.SUCCESS.getMessage()).geolocation(geolocation).build();
		return new ResponseEntity<ResponseBase>(respose, HttpStatus.OK);
	}

	/**
	 * Methos that add a new vehicle
	 * @param vehicle
	 * @return ResponseBase with the vehicle just added
	 */
	@PostMapping("/add")
	public ResponseEntity<ResponseBase> addVehicle(@Valid @RequestBody Vehicle vehicle) {

		vehicle = this.vehicleService.addVehicle(vehicle);
		ResponseBase response = ResponseBase.builder().success(Boolean.TRUE)
				.message(OperationResult.SUCCESS.getMessage()).vehicle(vehicle).build();
		return new ResponseEntity<ResponseBase>(response, HttpStatus.OK);

	}

	/**
	 * Method that perform an position update to the vehicle with the placa given
	 * @param placa
	 * @param geolocation
	 * @return ResponseBase with the boolean flag and the associated success message
	 */
	@PutMapping("/position")
	public ResponseEntity<ResponseBase> updatePosition(@RequestParam(name="placa") String placa,
			@RequestBody Geolocation geolocation) {
		Optional<double[]> coordiante = geolocation.getCoordinates().stream().findFirst();

		boolean success = this.vehicleService.updatePosition(placa, coordiante.get());
		ResponseBase response = ResponseBase.builder().success(success).success(Boolean.TRUE)
				.message(OperationResult.SUCCESS.getMessage()).build();
		return new ResponseEntity<ResponseBase>(response, HttpStatus.OK);

	}
}
