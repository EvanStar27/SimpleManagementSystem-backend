package io.github.managementsystem.managementsystem.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody UserDto user, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors()
                    .forEach(f -> {
                        errors.put(f.getField(), f.getDefaultMessage());
                    });

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        UserDto savedUser = userServices.registerUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
