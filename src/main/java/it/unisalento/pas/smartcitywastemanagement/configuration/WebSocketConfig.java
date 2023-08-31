package it.unisalento.pas.smartcitywastemanagement.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {




    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");  // abilita un message broker basato su memoria e imposta prefissi di destinazione
        config.setApplicationDestinationPrefixes("/app");  // prefisso per messaggi destinati a metodi annotati con @MessageMapping
    }



    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")   // imposta l'endpoint STOMP
                .setAllowedOrigins("http://localhost:4200")     // abilita CORS (modificarlo se conosci i tuoi origin specifici)
                .withSockJS();              // abilita supporto SockJS
    }

}
