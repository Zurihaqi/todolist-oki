package enigma.todo_list.utils.responseWrapper;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterResponse {
    public String username;
    public String email;
}
