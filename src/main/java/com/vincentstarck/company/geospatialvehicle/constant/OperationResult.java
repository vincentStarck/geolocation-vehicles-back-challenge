package com.vincentstarck.company.geospatialvehicle.constant;

/**
 * @author vincent
 *Enum to store some string constants used inside the application
 */
public enum OperationResult {

	SUCCESS("Operation executed successfully");

	private String message;

	private OperationResult(String message) {

		this.message = message;
	}

	public String getMessage() {

		return this.message;
	}
}
