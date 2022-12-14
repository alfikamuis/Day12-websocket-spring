package com.websocket.client.client.muis;

import com.websocket.client.client.alfika.ClientOneSessionHandler;
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

        ClientTwoSessionHandler clientTwoSessionHandler = new ClientTwoSessionHandler();
        ListenableFuture<StompSession> sessionAsync = stompClient.connect(
                "ws://localhost:8080/websocket-server", clientTwoSessionHandler
        );

        StompSession stompSession = sessionAsync.get();
        stompSession.subscribe("/topic/messages", clientTwoSessionHandler);

        int a = 1;
        while(a <= 10){
            stompSession.send(
                    "/topic/messages",
                    new Sender("muis "+ ": ini pesan ke-" + a
            ));
            a++;
            Thread.sleep(2000);
        }

    }
}
