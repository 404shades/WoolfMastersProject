package scaler.project.ecommercemasterbackendproject.ExceptionHandlers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import scaler.project.ecommercemasterbackendproject.exceptions.InvalidRoleException;
import scaler.project.ecommercemasterbackendproject.exceptions.UserAlreadyExistsException;

// has to run before GlobalExceptionHandler, otherwise its Exception.class handler
// catches BadCredentialsException etc and returns 500
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleUserAlreadyExists(UserAlreadyExistsException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        pd.setTitle("User Already Exists");
        pd.setProperty("email", e.getEmail());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(pd);
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ProblemDetail> handleInvalidRole(InvalidRoleException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        pd.setTitle("Invalid Role");
        pd.setProperty("role", e.getRole());
        return ResponseEntity.badRequest().body(pd);
    }

    // same message for wrong email and wrong password, so nobody can check which emails are registered
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ProblemDetail> handleAuthentication(AuthenticationException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        pd.setTitle("Unauthorized");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(pd);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ProblemDetail> handleAccessDenied(AccessDeniedException e) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "You don't have permission to do this");
        pd.setTitle("Forbidden");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(pd);
    }
}
