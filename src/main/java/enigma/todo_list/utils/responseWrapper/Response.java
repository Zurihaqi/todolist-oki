package enigma.todo_list.utils.responseWrapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class Response {
    public static <T> ResponseEntity<?> renderJSON(T data, String message, HttpStatus httpStatus) {
        WebResponse<T> response = WebResponse.<T>builder()
                .data(data)
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }

    public static <T> ResponseEntity<?> renderJSON(T data, String message) {
        return renderJSON(data, message, HttpStatus.OK);
    }

    public static <T> ResponseEntity<?> renderJSON(T data) {
        return renderJSON(data, "Success");
    }

//    public static ResponseEntity<?> renderError(String errors, HttpStatus httpStatus) {
//        WebResponseError response = WebResponseError.builder()
////                .status(httpStatus)
//                .error(errors)
//                .build();
//        return ResponseEntity.status(httpStatus).body(response);
//    }
}

