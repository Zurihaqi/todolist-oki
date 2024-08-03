package enigma.todo_list.service.serv;

import enigma.todo_list.model.meta.User;
import enigma.todo_list.utils.dto.RegisterRequestDTO;
import enigma.todo_list.utils.dto.UpdateRoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User create(RegisterRequestDTO req);
    User createSuperAdmin(RegisterRequestDTO req);
    Page<User> getAll(Pageable pageable);
    User getById(Integer id);
    User updateRoleById(Integer id, UpdateRoleDTO req);
}
