package com.hoaxify.ws;

import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserRepository;
import com.hoaxify.ws.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsApplication.class, args);
    }


    //	Method Bean ile Spring Container'a add olunanda methodda olan parametre autoinject olur
    @Bean
    public CommandLineRunner createInitialUsers(UserService userService) {
        return (args) -> {
            userService.save(new User(1, "user1", "display1", "P4ssword"));
        };
    }

//    Eger interface bagli1 method varsa lambda ile yazmaq olar yuxaridaki kimi
}
