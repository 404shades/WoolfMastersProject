package scaler.project.ecommercemasterbackendproject.services;

import scaler.project.ecommercemasterbackendproject.dtos.UserResponseDTO;
import scaler.project.ecommercemasterbackendproject.models.RoleName;

import java.util.List;

public interface UserService {

    public UserResponseDTO getUserByEmail(String email);
    public List<UserResponseDTO> getAllUsers(RoleName roleName);

}
