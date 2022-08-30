package com.day12.websocket;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Messages {
    private String messages;
    private String fromLogin;

    public Messages(String messages) {
        this.messages = messages;
    }
}
