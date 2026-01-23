package scaler.project.ecommercemasterbackendproject.services;

import scaler.project.ecommercemasterbackendproject.dtos.LoginRequestDTO;
import scaler.project.ecommercemasterbackendproject.dtos.LoginResponseDTO;
import scaler.project.ecommercemasterbackendproject.dtos.SignUpRequestDTO;
import scaler.project.ecommercemasterbackendproject.dtos.UserResponseDTO;
import scaler.project.ecommercemasterbackendproject.exceptions.InvalidRoleException;
import scaler.project.ecommercemasterbackendproject.exceptions.UserAlreadyExistsException;

public interface AuthService {

    public UserResponseDTO signUp(SignUpRequestDTO signUpRequest) throws UserAlreadyExistsException, InvalidRoleException;
    public LoginResponseDTO login(LoginRequestDTO loginRequest);

}
