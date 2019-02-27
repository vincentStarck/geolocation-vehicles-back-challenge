package com.vincentstarck.company.geospatialvehicle.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

/**
 * @author vincent
 * Clase used for custom exceptions
 */
@Data
public class GeospatialException extends RuntimeException {

	
	private HttpStatus status;

	private static final long serialVersionUID = 1L;
	
	public GeospatialException(String message) {
		super(message);
		this.status = HttpStatus.INTERNAL_SERVER_ERROR;

	}

	public GeospatialException(String message, HttpStatus status) {
		super(message);
		this.status = status;

	}

	public GeospatialException(String message, HttpStatus status, Throwable ex) {

		super(message, ex);
		this.status = status;
	}

}
