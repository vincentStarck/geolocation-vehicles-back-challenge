package com.vincentstarck.company.geospatialvehicle.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author vincent
 *classe used to model the Vehicle's geolocation data
 */
@Data
@Builder
@AllArgsConstructor
public class Geolocation implements Serializable {

	private final static String POINT_TYPE="MultiPoint";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * represent the point type
	 */
	private String type = POINT_TYPE;

	/**
	 * contain the vehicle's geolocation points
	 */
	private List<double[]> coordinates;

	

}
