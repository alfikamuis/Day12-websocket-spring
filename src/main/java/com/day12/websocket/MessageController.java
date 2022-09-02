package com.day12.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;


@RestController
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /*@MessageMapping("/sayHi")
    @SendTo("/topic/messages")
    public Messages sendMessages(Users users) throws InterruptedException {
        Thread.sleep(1000);
        return new Messages(HtmlUtils.htmlEscape(users.getName())+ ": ");
    }
     */

    @MessageMapping("/stomp-endpoint/{to}")
    public void sendMessageTo(@DestinationVariable String to, Messages messages) throws InterruptedException {
        System.out.println("Handling send messages: "+messages+" to "+to);
        Thread.sleep(1000);
        boolean isExist =  UserStorage.getInstance().getUsers().contains(to);
        if(isExist){
            simpMessagingTemplate.convertAndSend("/topic/messages/"+to,messages);
        }
    }
}
