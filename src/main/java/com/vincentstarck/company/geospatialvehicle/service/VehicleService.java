package com.vincentstarck.company.geospatialvehicle.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.vincentstarck.company.geospatialvehicle.exception.GeospatialException;
import com.vincentstarck.company.geospatialvehicle.model.Geolocation;
import com.vincentstarck.company.geospatialvehicle.model.Vehicle;
import com.vincentstarck.company.geospatialvehicle.respository.VehicleRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author vincent
 * Implement the IVehicleService methods
 */
@Service
@Slf4j
public class VehicleService implements IVehicleService {

	@Autowired
	private VehicleRepository vehicleRespository;

	@Autowired
	private IWebSocketService webSocketService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vehicle getVehicleByPlaca(String placa) {

		Optional<Vehicle> optVehicle = this.vehicleRespository.findByPlaca(placa);

		// check if vehicle exist in BD
		if (!optVehicle.isPresent()) {
			throw new GeospatialException("No se encontro auto con las placas:  " + placa);
		}
		return optVehicle.get();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vehicle addVehicle(Vehicle vehicle) {

		vehicle = this.vehicleRespository.insert(vehicle);
		// send notificatino to the clients connected by websocket
		this.webSocketService.sentNotification(vehicle);
		log.info("Se agregó vehiculo con placas {} exitosamente", vehicle.getPlaca());
		log.debug(new Gson().toJson(vehicle));

		return vehicle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Vehicle> getAllVehicles() {
		return this.vehicleRespository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean updatePosition(String placa, double[] coordiante) {

		Optional<Vehicle> optVehicle = this.vehicleRespository.findByPlaca(placa);

		if (!optVehicle.isPresent()) {
			throw new GeospatialException("No se encontro auto con las placas:  " + placa, HttpStatus.NOT_FOUND);
		}

		Vehicle vehicle = optVehicle.get();

		if (vehicle.getGeolocation() == null) {
			// add the first vehicle coordenate
			log.info("adding first vehicle coordenate");
			Geolocation geolocation = Geolocation.builder().build();
			List<double[]> coordianates = new ArrayList<>();
			coordianates.add(coordiante);
			geolocation.setCoordinates(coordianates);
			vehicle.setGeolocation(geolocation);

		} else {
			// adds new coordenates
			vehicle.getGeolocation().getCoordinates().add(coordiante);
		}

		// send notificatino to the clients connected by websocket
		this.webSocketService.sentNotification(vehicle);

		// update to database
		this.vehicleRespository.save(vehicle);
		log.info("Vehiculo con placas {} fue actualizada su posición", placa);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Geolocation getCurrentPosition(String placa) {

		Geolocation geolocation = Geolocation.builder().build();
		Optional<Vehicle> optVehicle = this.vehicleRespository.findByPlaca(placa);

		if (!optVehicle.isPresent()) {

			throw new GeospatialException("No se encontró auto con las placas:  " + placa, HttpStatus.NOT_FOUND);

		}

		if (optVehicle.get().getGeolocation() == null) {

			throw new GeospatialException("Auto con placa:  " + placa + " No tiene registro de ubicación",
					HttpStatus.NOT_FOUND);
		}

		double[] currentPosition = Iterables.getLast(optVehicle.get().getGeolocation().getCoordinates());
		List<double[]> positions = new ArrayList<double[]>();
		positions.add(currentPosition);
		geolocation.setCoordinates(positions);

		return geolocation;
	}

}
