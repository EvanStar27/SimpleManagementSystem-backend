package io.github.managementsystem.managementsystem.Config;

import io.github.managementsystem.managementsystem.Roles.RoleService;
import io.github.managementsystem.managementsystem.Roles.UserRoleMapping;
import io.github.managementsystem.managementsystem.Roles.UserRoleMappingService;
import io.github.managementsystem.managementsystem.Users.User;
import io.github.managementsystem.managementsystem.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleMappingService userRoleMappingService;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (User) userRepository.loadUserByUsername(username);

        List<UserRoleMapping> byUserId = userRoleMappingService.findByUserId(user.getUserId());

        List<GrantedAuthority> authorities = byUserId.stream()
                .map(r -> new SimpleGrantedAuthority(roleService.findRoleById(r.getRoleId())
                .getRoleName())).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
