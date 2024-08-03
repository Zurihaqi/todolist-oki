package enigma.todo_list.service.impl;

import enigma.todo_list.config.advisers.exception.NotFoundException;
import enigma.todo_list.model.enums.Role;
import enigma.todo_list.model.meta.User;
import enigma.todo_list.repo.UserRepository;
import enigma.todo_list.service.serv.AuthenticationService;
import enigma.todo_list.service.serv.UserService;
import enigma.todo_list.utils.dto.RegisterRequestDTO;
import enigma.todo_list.utils.dto.UpdateRoleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    @Override
    public User create(RegisterRequestDTO req) {
        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.USER)
                .build();
        return userRepository.save(user);
    }

    @Override
    public User createSuperAdmin(RegisterRequestDTO req) {
        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.SUPER_ADMIN)
                .build();
        return userRepository.save(user);
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findById(id).orElseThrow( () -> new NotFoundException("User Not Found"));
    }

    @Override
    public User updateRoleById(Integer id, UpdateRoleDTO req) {
        User user = getById(id);
        updateUserRole(user, req);
        return userRepository.save(user);
    }

    private void updateUserRole(User user, UpdateRoleDTO req) {
        if (req.getRole() != null) {
            user.setRole(req.getRole());
        }
    }
}
