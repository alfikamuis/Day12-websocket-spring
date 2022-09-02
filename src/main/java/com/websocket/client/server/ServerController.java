package com.websocket.client.server;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ServerController {

    @MessageMapping("/process-message")
    @SendTo("/topic/messages")
    public Message theMessage(Sender sender) throws Exception {
        Thread.sleep(1000);
        return new Message(sender.getName()+" : ");
    }
}
