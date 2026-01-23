package scaler.project.ecommercemasterbackendproject.exceptions;

import scaler.project.ecommercemasterbackendproject.models.RoleName;

public class InvalidRoleException extends Exception {
    private final RoleName role;

    public InvalidRoleException(RoleName role) {
        super("Signing up with role " + role + " is not allowed");
        this.role = role;
    }

    public RoleName getRole() {
        return role;
    }
}
