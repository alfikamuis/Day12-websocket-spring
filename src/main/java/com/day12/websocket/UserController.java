package com.day12.websocket;

import org.apache.catalina.User;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class UserController {

    @GetMapping("registration/{username}")
    public ResponseEntity<Void> register(@PathVariable String username){
        System.out.println("Handling register user req " + username);
        try {
            UserStorage.getInstance().setUsers(username);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        return  ResponseEntity.ok().build();
    }

    @GetMapping("/fetchAllUsers")
    public Set<String> fetchAll(){
        return UserStorage.getInstance().getUsers();
    }
}
