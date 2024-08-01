package enigma.todo_list.controller;

import enigma.todo_list.service.serv.AuthenticationService;
import enigma.todo_list.utils.dto.AuthenticationRequestDTO;
import enigma.todo_list.utils.dto.AuthenticationResponseDTO;
import enigma.todo_list.utils.dto.RegisterRequestDTO;
import enigma.todo_list.utils.responseWrapper.MyResponse;
import enigma.todo_list.utils.responseWrapper.WebResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/api/auth")
@RestControllerAdvice
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO req) {
        return new ResponseEntity<>(new WebResponse<>("Register Success", HttpStatus.CREATED, service.register(req)), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO req) {
        return new ResponseEntity<>(new WebResponse<>("Login Successfully", HttpStatus.OK, service.authenticate(req)), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }
}
