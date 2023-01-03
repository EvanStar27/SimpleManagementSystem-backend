package io.github.managementsystem.managementsystem.Auth;

import io.github.managementsystem.managementsystem.Config.CustomUserDetailService;
import io.github.managementsystem.managementsystem.Exceptions.DuplicateKeyException;
import io.github.managementsystem.managementsystem.JWT.JwtRequest;
import io.github.managementsystem.managementsystem.JWT.JwtResponse;
import io.github.managementsystem.managementsystem.JWT.JwtTokenUtil;
import io.github.managementsystem.managementsystem.Roles.Role;
import io.github.managementsystem.managementsystem.Roles.RoleService;
import io.github.managementsystem.managementsystem.Roles.UserRoleMapping;
import io.github.managementsystem.managementsystem.Roles.UserRoleMappingService;
import io.github.managementsystem.managementsystem.Users.User;
import io.github.managementsystem.managementsystem.Users.UserDto;
import io.github.managementsystem.managementsystem.Users.UserRepository;
import io.github.managementsystem.managementsystem.Users.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/")
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServices userServices;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleMappingService userRoleMappingService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (DisabledException e) {
            throw new Exception("USER DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID CREDENTIALS", e);
        }

        final UserDetails userDetails =
                customUserDetailService.loadUserByUsername(jwtRequest.getUsername());

        final User user = (User) userRepository.loadUserByUsername(userDetails.getUsername());

        List<UserRoleMapping> userRoleMappingList = userRoleMappingService.findByUserId(user.getUserId());
        List<Role> roleList = new ArrayList<>();

        userRoleMappingList.forEach(userRoleMapping -> {
            roleList.add(roleService.findRoleById(userRoleMapping.getRoleId()));
        });

        roleList.forEach(role -> {
            user.getRoles().add(role);
        });

        final String token =
                jwtTokenUtil.generateToken(user);

        JwtResponse response = new JwtResponse();

        response.setToken(token);
        roleList.forEach((role)-> response.setRole(role.getRoleName()));
        response.setUserId(user.getUserId());
        return response;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto user, BindingResult bindingResult) throws DuplicateKeyException {
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
