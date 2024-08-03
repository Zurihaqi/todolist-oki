package enigma.todo_list.controller;

import enigma.todo_list.model.meta.TodoItem;
import enigma.todo_list.service.serv.TodoService;
import enigma.todo_list.utils.dto.TodoDTO;
import enigma.todo_list.utils.dto.UpdateStatusDTO;
import enigma.todo_list.utils.responseWrapper.Response;
import enigma.todo_list.utils.responseWrapper.TodoRespone;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RestControllerAdvice
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoController {
    @Autowired
    private final TodoService service;

    @PostMapping("/todos")
    public ResponseEntity<?> createTodoItem(@Valid @RequestBody TodoDTO obj) {
        TodoItem item = service.create(obj);
        TodoRespone respone = TodoRespone.builder()
                .id(String.valueOf(item.getId()))
                .title(item.getTitle())
                .description(item.getDescription())
                .createdAt(String.valueOf(item.getCreatedDate()))
                .dueDate(item.getDueDate())
                .status(String.valueOf(item.getStatus()))
                .build();
        return new ResponseEntity<>(respone, HttpStatus.CREATED);
//        return Response.renderJSON(service.create(obj), "Todo Item had Created", HttpStatus.CREATED);
    }

    @GetMapping("/todos")
    public ResponseEntity<?> findUserTodoItem() {
        return new ResponseEntity<>(service.findAllByUser(), HttpStatus.OK);
//        return Response.renderJSON(service.findAllByUser(), "Success", HttpStatus.OK);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> findUserTodoItem(@PathVariable Integer id) {
        return Response.renderJSON(service.findByUser(id), "Success", HttpStatus.OK);
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateTodoItem(@Valid @PathVariable Integer id, @RequestBody TodoDTO obj) {
        return Response.renderJSON(service.update(id, obj), "Success", HttpStatus.OK);
    }

    @PatchMapping("/todos/{id}/status")
    public ResponseEntity<?> updateTodoItemStatus(@Valid @PathVariable Integer id, @RequestBody UpdateStatusDTO obj) {
        return Response.renderJSON(
                service.updateStatus(id, obj),
                "Todo Status Has Been Updated",
                HttpStatus.OK);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteTodoItemById(@PathVariable Integer id) {
        service.deleteById(id);
        return Response.renderJSON(null, "Item Has Been Deleted", HttpStatus.NO_CONTENT);
    }

    /*
    *
    * Rule by Admin & Super Admin
    * Get Item
     */
    @GetMapping("/admin/todos")
    public ResponseEntity<?> findAll() {
        return Response.renderJSON(service.findAll(), "Success", HttpStatus.OK);
    }

    @GetMapping("/admin/todos/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id){
        return Response.renderJSON(service.findById(id), "Success", HttpStatus.OK);
    }
}
