package enigma.todo_list.service.serv;

import enigma.todo_list.model.meta.User;
import enigma.todo_list.utils.dto.AuthenticationRequestDTO;
import enigma.todo_list.utils.dto.AuthenticationResponseDTO;
import enigma.todo_list.utils.dto.RegisterRequestDTO;
import enigma.todo_list.utils.responseWrapper.MyResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponseDTO register(RegisterRequestDTO request);

    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    User getUserAuthenticated();

    void saveUserToken(User user, String jwtToken);
}
