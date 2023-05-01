package com.hoaxify.ws.user;


import com.hoaxify.ws.shared.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {


    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService  userService;

    @PostMapping("/api/1.0/users")
//    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse createUser(@RequestBody User user) {
        userService.save(user);
        return new GenericResponse("user created");
    }


}
