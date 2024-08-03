package enigma.todo_list.controller;

import enigma.todo_list.config.advisers.exception.MissingFieldException;
import enigma.todo_list.model.meta.User;
import enigma.todo_list.service.serv.AuthenticationService;
import enigma.todo_list.utils.dto.AuthenticationRequestDTO;
import enigma.todo_list.utils.dto.RegisterRequestDTO;
import enigma.todo_list.utils.responseWrapper.UserRegisterResponse;
import enigma.todo_list.utils.responseWrapper.WebResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@RestControllerAdvice
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO req) {
        if (req.getEmail() == null || req.getEmail().isEmpty()) {
            throw new MissingFieldException("email is missing");
        }else if (req.getPassword() == null || req.getPassword().isEmpty()) {
            throw new MissingFieldException("password is missing");
        }else{
        User user = service.register(req);
        UserRegisterResponse userResponse = UserRegisterResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO req) {
        if (req.getEmail() == null || req.getEmail().isEmpty()) {
            throw new MissingFieldException("email is missing");
        }else if (req.getPassword() == null || req.getPassword().isEmpty()) {
            throw new MissingFieldException("password is missing");
        }else{
            return new ResponseEntity<>(service.authenticate(req), HttpStatus.OK);
        }
    }

    @PostMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }
}
