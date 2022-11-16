package io.github.managementsystem.managementsystem.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    private BigInteger roleId;

    @NotBlank(message = "Role Name is required")
    private String roleName;
}
