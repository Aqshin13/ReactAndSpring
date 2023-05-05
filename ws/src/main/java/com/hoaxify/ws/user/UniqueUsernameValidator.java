package com.hoaxify.ws.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

//<UniqueUsername,String(yeni username fieldi string oldugu ucun String type secirik)>

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
//       username username degerine beraber olacaq

        User user = userRepository.findByUsername(username);

        if (user != null) {
            return false;
        }

        return true;
    }
}
