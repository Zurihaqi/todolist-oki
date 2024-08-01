package enigma.todo_list.model.meta;

import enigma.todo_list.model.enums.TodoStatus;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "todo_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", length = 100, nullable = false)
    @NotNull
    private String title;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    private String dueDate;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TodoStatus status;

    @Column(name = "CreatedDate", nullable = false)
    @CreationTimestamp
    private LocalDate createdDate;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
