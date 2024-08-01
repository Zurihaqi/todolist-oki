package enigma.todo_list.repo;

import enigma.todo_list.model.meta.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoItem, Integer> {
    @Query(value = "SELECT * FROM todo_item WHERE user_id = :userId", nativeQuery = true)
    List<TodoItem> findAllByUserId(@Param("userId") Integer userId);
}
