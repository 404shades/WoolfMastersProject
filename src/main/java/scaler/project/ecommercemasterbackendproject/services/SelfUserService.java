package scaler.project.ecommercemasterbackendproject.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import scaler.project.ecommercemasterbackendproject.dtos.UserResponseDTO;
import scaler.project.ecommercemasterbackendproject.models.RoleName;
import scaler.project.ecommercemasterbackendproject.models.User;
import scaler.project.ecommercemasterbackendproject.repositories.UserRepository;

import java.util.List;

@Service
public class SelfUserService implements UserService {
    UserRepository userRepository;

    public SelfUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserResponseDTO::from)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email " + email));
    }

    @Override
    public List<UserResponseDTO> getAllUsers(RoleName roleName) {
        List<User> users = roleName == null
                ? userRepository.findAll()
                : userRepository.findAllByRoles_Name(roleName);
        return users.stream()
                .map(UserResponseDTO::from)
                .toList();
    }
}
