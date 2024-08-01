package enigma.todo_list.utils.dto;

import enigma.todo_list.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleDTO {
    private Role role;
}
