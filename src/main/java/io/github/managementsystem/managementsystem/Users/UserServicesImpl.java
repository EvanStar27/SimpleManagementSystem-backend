package io.github.managementsystem.managementsystem.Users;

import io.github.managementsystem.managementsystem.Exceptions.DuplicateKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerUser(UserDto user) throws DuplicateKeyException {
        UserDto userDto = UserDto.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        return userRepository.save(userDto);
    }
}
