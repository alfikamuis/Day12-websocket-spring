package com.websocket.client.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private String message;

    public Message(String content) {
        this.message = content;
    }

    public Message() {
    }

}

