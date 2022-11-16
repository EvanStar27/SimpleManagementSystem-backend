package io.github.managementsystem.managementsystem.Utils;


import io.github.managementsystem.managementsystem.Roles.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class RoleUtil {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleMappingRepository userRoleMappingRepository;

    public List<SimpleGrantedAuthority> getAuthorities(BigInteger userId) {

        List<UserRoleMapping> byUserId = userRoleMappingRepository.findByUserId(userId);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        byUserId.forEach(user -> {
            authorities.add(
                    new SimpleGrantedAuthority(
                            roleRepository.findRoleById(user.getRoleId())
                                    .getRoleName())
            );
        });

        return authorities;
    }
}
