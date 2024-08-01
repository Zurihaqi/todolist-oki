package enigma.todo_list.service.serv;

import enigma.todo_list.model.meta.TodoItem;
import enigma.todo_list.utils.dto.TodoDTO;
import enigma.todo_list.utils.dto.UpdateStatusDTO;

import java.util.List;

public interface TodoService {
    TodoItem create(TodoDTO obj);

    List<TodoItem> findAll();

    TodoItem findById(Integer id);

    TodoItem update(Integer id, TodoDTO obj);

    TodoItem updateStatus(Integer id, UpdateStatusDTO obj);

    void deleteById(Integer id);

    TodoItem findByUser(Integer id);

    List<TodoItem> findAllByUser();

}
