package com.vincentstarck.company.geospatialvehicle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author vincent
 *Class que define the webSocket configuation
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Value("${destination.prefix}")
	private String destinationPrefix;
	@Value("${prefixe}")
	private String prefixe;
	@Value("${websocket.endpoint}")
	private String endPoint;

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker(destinationPrefix);
		config.setApplicationDestinationPrefixes(prefixe);
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(endPoint).setAllowedOrigins("*").withSockJS();

	}

}