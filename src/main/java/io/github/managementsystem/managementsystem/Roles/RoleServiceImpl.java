package io.github.managementsystem.managementsystem.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findRoleById(BigInteger roleId) {
        return roleRepository.findRoleById(roleId);
    }
}
