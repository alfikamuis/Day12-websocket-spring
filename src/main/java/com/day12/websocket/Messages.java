package com.day12.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
