package scaler.project.ecommercemasterbackendproject.dtos;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String accessToken;
    private String tokenType;
    private Long expiresInMs;
}
