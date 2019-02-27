package com.vincentstarck.company.geospatialvehicle.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author vincent
 *Clase used for model the custom error messages to the client
 */
@Data
@Builder
public class BaseError {
	
	private String timestamp;
	private String message;

}
