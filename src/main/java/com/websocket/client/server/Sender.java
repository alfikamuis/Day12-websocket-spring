package com.websocket.client.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Sender {
    private String name;

    public Sender(String name) {
        this.name = name;
    }

    public Sender() {
    }
}
