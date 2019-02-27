package com.vincentstarck.company.geospatialvehicle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

/**
 * @author vincent
 *Implement the IWebSocketService interface
 */
@Service
@Slf4j
public class WebSocketService implements IWebSocketService {

	@Value("${destination}")
	private String destination;

	@Autowired
	private SimpMessagingTemplate template;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sentNotification(Object payLoad) {
		try {
			this.template.convertAndSend(destination, payLoad);
		} catch (MessagingException ex) {
			log.error("Error to send notification by websocket {}", ex);
		}
		log.info("Notification sent to destination: {}", destination);
		log.debug(new Gson().toJson(payLoad));
	}

}
