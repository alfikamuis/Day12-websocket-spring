package com.websocket.client.client.alfika;

import com.websocket.client.server.Sender;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

public class ClienOne {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        ClienOneSessionHandler clienOneSessionHandler = new ClienOneSessionHandler();
        ListenableFuture<StompSession> sessionAsync = stompClient.connect(
                "ws://localhost:8080/websocket-server", clienOneSessionHandler
        );

        StompSession stompSession = sessionAsync.get();
        stompSession.subscribe("/topic/messages",clienOneSessionHandler);

        while(true){
            stompSession.send(
                    "/app/process-message",
                    new Sender("muis"+ System.currentTimeMillis()
            ));
            Thread.sleep(2000);
        }

    }
}
