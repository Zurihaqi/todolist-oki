package enigma.todo_list.utils.responseWrapper;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebResponseError {
    private String error;
//    private HttpStatus status;
}