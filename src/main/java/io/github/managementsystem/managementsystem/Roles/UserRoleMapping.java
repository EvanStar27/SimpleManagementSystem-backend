package io.github.managementsystem.managementsystem.Roles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleMapping {
    private BigInteger urMappingId;

    private BigInteger userId;

    private BigInteger roleId;
}
