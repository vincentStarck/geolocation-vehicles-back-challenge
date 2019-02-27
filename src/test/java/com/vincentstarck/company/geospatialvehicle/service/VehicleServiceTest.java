package com.vincentstarck.company.geospatialvehicle.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.vincentstarck.company.geospatialvehicle.exception.GeospatialException;
import com.vincentstarck.company.geospatialvehicle.model.Geolocation;
import com.vincentstarck.company.geospatialvehicle.model.Vehicle;
import com.vincentstarck.company.geospatialvehicle.respository.VehicleRepository;
import com.vincentstarck.company.geospatialvehicle.service.VehicleService;
import com.vincentstarck.company.geospatialvehicle.service.WebSocketService;

@RunWith(MockitoJUnitRunner.class)
public class VehicleServiceTest {

	@Mock
	VehicleRepository vehicleRespository;

	@Mock
	WebSocketService webSocketService;

	@InjectMocks
	VehicleService vehicleService = new VehicleService();

	/**
	 * Execute tests when a vehicule's position is updated
	 */
	@Test
	public void testUpdatePosition() {

		String placa = "MZY-5456";
		double[] coordenate = { 20.45, -44.0 };
		Optional<Vehicle> optVehicle = Optional.of(Vehicle.builder().placa(placa).build());
		Mockito.when(vehicleRespository.findByPlaca(placa)).thenReturn(optVehicle);

		// execute test when the vehicle doesn't have any coordenates
		boolean result = vehicleService.updatePosition(placa, coordenate);
		assertEquals(true, result);

		// execute test when the vehicle already has some coordeantes
		Geolocation geolocation = Geolocation.builder().coordinates(Arrays.asList(coordenate)).build();
		optVehicle = Optional.of(Vehicle.builder().placa(placa).geolocation(geolocation).build());
		result = vehicleService.updatePosition(placa, coordenate);
		assertEquals(true, result);

	}

	/**
	 * Execute a test when the there is not a vehicle with the placa given, then the
	 * method must to throw an Custom Exception
	 */
	@Test(expected = GeospatialException.class)
	public void testUpdatePositionThrowsExceptionIfVehicleIsNull() {
		String placa = "MZY-5456";
		double[] coordenate = { 20.45, -44.0 };
		Optional<Vehicle> optVehicle = Optional.ofNullable(null);
		Mockito.when(vehicleRespository.findByPlaca(placa)).thenReturn(optVehicle);

		boolean result = vehicleService.updatePosition(placa, coordenate);
		assertEquals(true, result);

	}

	/**
	 * Test that the method recovery the current vehicle position
	 */
	@Test
	public void testGetCurrentPosition() {
		String placa = "MZY-5456";

		List<double[]> listCoordenates = new ArrayList<double[]>();

		double[] lastCoordiante = { 13.98, -80.87 };
		listCoordenates.add(new double[] { 35.98, -33.454 });
		listCoordenates.add(new double[] { 56.98, -45.77 });
		listCoordenates.add(lastCoordiante);

		Geolocation geolocation = Geolocation.builder().coordinates(listCoordenates).build();

		Optional<Vehicle> optVehicle = Optional.of(Vehicle.builder().placa(placa).geolocation(geolocation).build());
		Mockito.when(vehicleRespository.findByPlaca(placa)).thenReturn(optVehicle);
		Geolocation result = vehicleService.getCurrentPosition(placa);

		int lengthResult = result.getCoordinates().size();
		assertEquals(lastCoordiante.equals(result.getCoordinates().get(lengthResult - 1)), true);

	}

	/**
	 * Test that the method throw an exception if the vehicle doesn`t exit in BD
	 */
	@Test(expected = GeospatialException.class)
	public void testGetCurrentPositionThrowExceptionIfVehicleNotExist() {
		String placa = "MZY-5456";
		double[] lastCoordiante = { 13.98, -80.87 };

		Optional<Vehicle> optVehicle = Optional.ofNullable(null);
		Mockito.when(vehicleRespository.findByPlaca(placa)).thenReturn(optVehicle);
		Geolocation result = vehicleService.getCurrentPosition(placa);

		int lengthResult = result.getCoordinates().size();
		assertEquals(lastCoordiante.equals(result.getCoordinates().get(lengthResult - 1)), true);

	}

	/**
	 * Test that the method throw an exception if the vehicle's geolocation object
	 * doesn`t exit
	 */
	@Test(expected = GeospatialException.class)
	public void testGetCurrentPositionThrowExceptionIfGeolocationNotExist() {
		String placa = "MZY-5456";
		double[] lastCoordiante = { 13.98, -80.87 };

		/*
		 * The Geolocation object is not create, therefore the invocation call throw an
		 * GesopatiolExceptions
		 */
		Optional<Vehicle> optVehicle = Optional.of(Vehicle.builder().placa(placa).build());
		Mockito.when(vehicleRespository.findByPlaca(placa)).thenReturn(optVehicle);
		Geolocation result = vehicleService.getCurrentPosition(placa);

		int lengthResult = result.getCoordinates().size();
		assertEquals(lastCoordiante.equals(result.getCoordinates().get(lengthResult - 1)), true);

	}

}
