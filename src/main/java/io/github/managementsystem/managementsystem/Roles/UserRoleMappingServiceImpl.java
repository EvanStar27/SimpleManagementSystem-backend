package io.github.managementsystem.managementsystem.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class UserRoleMappingServiceImpl implements UserRoleMappingService {

    @Autowired
    private UserRoleMappingRepository userRoleMappingRepository;

    @Override
    public List<UserRoleMapping> findByUserId(BigInteger userId) {
        return userRoleMappingRepository.findByUserId(userId);
    }
}
