package scaler.project.ecommercemasterbackendproject.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import scaler.project.ecommercemasterbackendproject.models.RoleName;

@Data
public class SignUpRequestDTO {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "email is not valid")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 8, message = "password should be at least 8 characters")
    private String password;

    // optional, CUSTOMER if not sent. ADMIN is not allowed from signup
    private RoleName role;
}
