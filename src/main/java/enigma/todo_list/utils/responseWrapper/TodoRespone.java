package enigma.todo_list.utils.responseWrapper;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoRespone {
    private String id;
    private String title;
    private String description;
    private String dueDate;
    private String status;
    private String createdAt;

}
