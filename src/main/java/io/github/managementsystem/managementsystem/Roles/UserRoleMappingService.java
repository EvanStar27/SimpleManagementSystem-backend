package io.github.managementsystem.managementsystem.Roles;

import java.math.BigInteger;
import java.util.List;

public interface UserRoleMappingService {
    List<UserRoleMapping> findByUserId(BigInteger userId);
}
