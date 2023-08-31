package it.unisalento.pas.smartcitywastemanagement.controller;

import it.unisalento.pas.smartcitywastemanagement.security.JwtUtilities;
import it.unisalento.pas.smartcitywastemanagement.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Controller
public class WebSocketController {

    @Autowired
    private JwtUtilities jwtUtilities;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final String SECRET_KEY = "bZ4t7#RcQ!p9W0yJ";
    private static final String INIT_VECTOR = "1234567890123456";     // sostituire con una chiave IV adeguata

    @MessageMapping("/authenticate")
    public void receiveMessage(@Payload String encryptedMessage,SimpMessageHeaderAccessor headerAccessor) {
        try {
            String decryptedMessage = decrypt(encryptedMessage, SECRET_KEY, INIT_VECTOR);


            System.out.println("Token JWT ricevuto: " + decryptedMessage);

            // Validazione del token JWT
            String username = jwtUtilities.extractUsername(decryptedMessage);
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
            if (!jwtUtilities.validateToken(decryptedMessage, userDetails)) {
                System.out.println("Token JWT non valido. Chiudo la connessione.");



            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Errore durante la decrittazione del messaggio: " + e.getMessage());


        }
    }

    private String decrypt(String encrypted, String key, String initVector) throws Exception {
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));

        return new String(original);
    }
}
