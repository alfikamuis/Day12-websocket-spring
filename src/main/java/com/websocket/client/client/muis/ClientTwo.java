package com.websocket.client.client.muis;

import com.websocket.client.server.Sender;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

public class ClientTwo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        ClienTwoSessionHandler clienTwoSessionHandler = new ClienTwoSessionHandler();
        ListenableFuture<StompSession> sessionAsync = stompClient.connect(
                "ws://localhost:8080/websocket-server", clienTwoSessionHandler
        );

        StompSession stompSession = sessionAsync.get();
        stompSession.subscribe("/topic/messages",clienTwoSessionHandler);

        while(true){
            stompSession.send(
                    "/app/process-message",
                    new Sender("alfika"+ System.currentTimeMillis()
            ));
            Thread.sleep(2000);
        }

    }
}
