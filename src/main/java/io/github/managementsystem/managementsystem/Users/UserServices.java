package io.github.managementsystem.managementsystem.Users;

import io.github.managementsystem.managementsystem.Exceptions.DuplicateKeyException;

public interface UserServices {
    UserDto registerUser(UserDto user) throws DuplicateKeyException;
}
