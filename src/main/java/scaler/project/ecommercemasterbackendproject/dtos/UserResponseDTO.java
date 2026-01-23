package scaler.project.ecommercemasterbackendproject.dtos;

import lombok.Data;
import scaler.project.ecommercemasterbackendproject.models.Role;
import scaler.project.ecommercemasterbackendproject.models.RoleName;
import scaler.project.ecommercemasterbackendproject.models.User;

import java.util.Set;
import java.util.stream.Collectors;

// what we send back for a user, so the password hash never leaves the server
@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Set<RoleName> roles;

    public static UserResponseDTO from(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet()));
        return dto;
    }
}
