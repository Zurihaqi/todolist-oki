package enigma.todo_list.service.impl;

import enigma.todo_list.config.advisers.exception.NotFoundException;
import enigma.todo_list.model.enums.TodoStatus;
import enigma.todo_list.model.meta.TodoItem;
import enigma.todo_list.model.meta.User;
import enigma.todo_list.repo.TodoRepository;
import enigma.todo_list.repo.UserRepository;
import enigma.todo_list.service.serv.AuthenticationService;
import enigma.todo_list.service.serv.TodoService;
import enigma.todo_list.utils.dto.TodoDTO;
import enigma.todo_list.utils.dto.UpdateStatusDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    @Transactional
    public TodoItem create(TodoDTO obj) {
        User user = authenticationService.getUserAuthenticated();

        TodoItem todoItem = TodoItem.builder()
                .title(obj.getTitle())
                .description(obj.getDescription())
                .user(user)
                .dueDate(obj.getDue_date())
                .status(obj.getStatus() != null ? TodoStatus.valueOf(obj.getStatus()) : TodoStatus.IN_PROGRESS)
                .createdDate(LocalDate.now())
                .build();

        return todoRepository.save(todoItem);
    }

    @Override
    public List<TodoItem> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public TodoItem findById(Integer id) {
        return todoRepository.findById(id).orElseThrow( () -> new RuntimeException("Task Not Found"));
    }

    @Override
    public TodoItem update(Integer id, TodoDTO obj) {
        User user = authenticationService.getUserAuthenticated();
        TodoItem todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Task Not Found"));

        todo.setTitle(obj.getTitle());
        todo.setDescription(obj.getDescription());
        todo.setUser(user);
        todo.setStatus(TodoStatus.valueOf(obj.getStatus()));

        return todoRepository.save(todo);
    }

    @Override
    public TodoItem updateStatus(Integer id, UpdateStatusDTO obj) {
        TodoItem todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Task Not Found"));

        updateTodoStatus(todo, obj);

        return todoRepository.save(todo);
    }

    private void updateTodoStatus(TodoItem todo, UpdateStatusDTO req) {
        if (req.getStatus() != null) {
            todo.setStatus(TodoStatus.valueOf(req.getStatus()));
        }
    }

    @Override
    public void deleteById(Integer id) {
        if (todoRepository.existsById(id)){
            TodoItem temp = todoRepository.findById(id).orElseThrow();
            todoRepository.deleteById(id);
            System.out.println("Task dengan Judul: \""+temp.getTitle()+"\" Berhasil dihapus");
        }else {
            throw new NotFoundException("Category dengan ID " + id + "tidak ditemukan");
        }
    }

    @Override
    public TodoItem findByUser(Integer id) {
        return todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo Item Not Found"));
    }

    @Override
    public List<TodoItem> findAllByUser() {
        User user = authenticationService.getUserAuthenticated();
        return user.getTasks();
    }
}
