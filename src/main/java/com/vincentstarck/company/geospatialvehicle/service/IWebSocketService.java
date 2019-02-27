package com.vincentstarck.company.geospatialvehicle.service;

/**
 * @author vincent
 * Define the contract associated to the WebSocketService class
 */
public interface IWebSocketService {

	/**
	 * Send notificatio to the websocket 
	 * @param payLoad
	 */
	void sentNotification(Object payLoad);

}
