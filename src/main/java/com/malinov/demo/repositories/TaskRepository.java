package com.malinov.demo.repositories;

import com.malinov.demo.enums.State;
import com.malinov.demo.enums.TaskType;
import com.malinov.demo.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByState(State state);
    List<Task> findByCreatedByIdOrAssignToIdAndState(Long createdBy_id, Long assignTo_id, State state);
    List<Task> findByCreatedByIdOrAssignToIdAndTypeAndState(Long createdBy_id, Long assignTo_id, TaskType type, State state);
    Task findByIdAndState(Long id, State state);
}
