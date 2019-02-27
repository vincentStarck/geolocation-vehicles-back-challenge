package com.vincentstarck.company.geospatialvehicle.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.vincentstarck.company.geospatialvehicle.exception.GeospatialException;
import com.vincentstarck.company.geospatialvehicle.model.BaseError;

import lombok.extern.slf4j.Slf4j;

/**
 *Global handler exception for this proyect
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

	
	private SimpleDateFormat dataFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
	
	/**
	 * Handle RutimeExceptionClass
	 *
	 **/
	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<BaseError> handleUserException(RuntimeException ex) {

		BaseError baseError = BaseError.builder()
				.message(ex.getMessage()) //retrieve the error message
				.timestamp(dataFormat.format(new Date())).build();
		
		log.error("traza del error : ", ex);
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		
		//check if the ex object is an instance of GeospatialException, if yes, I retry the httpStatus code
         if(ex instanceof GeospatialException) {
        	 status = ((GeospatialException)ex).getStatus();
        	 
         }		
  
		return new ResponseEntity<BaseError>(baseError, status);

	}
	
	
}
