package com.day12.websocket;

import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class UserStorage {
    private static UserStorage instance;
    private Set<String> users;

    private UserStorage(){
        users = new HashSet<>();
    }

    public static synchronized UserStorage getInstance(){
        if (instance == null){
            instance = new UserStorage();
        }
        return instance;
    }

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(String username) throws Exception { //check if user taken or not
        if(users.contains(username)){
            throw new Exception("user "+username+" already exist!");
        }
        users.add(username);
    }
}
