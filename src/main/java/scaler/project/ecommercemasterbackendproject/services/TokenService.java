package scaler.project.ecommercemasterbackendproject.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {

    public String generateToken(UserDetails userDetails);
    public String extractUsername(String token);
    public boolean isTokenValid(String token, UserDetails userDetails);
    public long getExpirationMs();

}
