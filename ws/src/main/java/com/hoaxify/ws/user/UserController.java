package com.hoaxify.ws.user;


import com.hoaxify.ws.error.ApiError;
import com.hoaxify.ws.shared.GenericResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {


    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @PostMapping("/api/1.0/users")
//    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse createUser(@Valid @RequestBody User user) {
        userService.save(user);
        return new GenericResponse("user created");
    }


//    Classa aid handle
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MethodArgumentNotValidException exception) {
        ApiError error = new ApiError(400, "Validation Error", "/api/1.0/users");
        Map<String, String> validationErrors = new HashMap<>();

        for (FieldError fieldError:exception.getBindingResult().getFieldErrors()){
         validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        error.setValidationErrors(validationErrors);
        return error;
    }



//    Validasiyani ozumuz edirdik amma sonra Springe verdik
//    @PostMapping("/api/1.0/users")
////    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<?> createUser(@RequestBody User user) {
//        ApiError error = new ApiError(400, "Validation Error", "/api/1.0/users");
//        Map<String, String> validationErrors = new HashMap<>();
//        String username = user.getUsername();
//        String displayName = user.getDisplayName();
//
//        if (username == null || username.isEmpty()) {
//            validationErrors.put("username", "Username cannot be null");
//        }
//
//
//        if (displayName == null || displayName.isEmpty()) {
//            validationErrors.put("displayName", "Display name cannot be null");
//        }
//
//        if (validationErrors.size() > 0) {
//            error.setValidationErrors(validationErrors);
//            return ResponseEntity.badRequest().body(error);
//        }
//        userService.save(user);
//        return ResponseEntity.ok(new GenericResponse("user created"));
//    }


}
