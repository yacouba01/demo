package com.malinov.demo.services.task;

import com.malinov.demo.dto.TaskResponse;
import com.malinov.demo.enums.State;
import com.malinov.demo.enums.TaskType;
import com.malinov.demo.models.Task;

import java.util.List;

public interface TaskService {

    TaskResponse save(Task task);
    List<TaskResponse> findByState(State state);
    List<TaskResponse> findByAuthAndState(State state);
    List<TaskResponse> findByAuthAndTypeAndState(TaskType type, State state);
    TaskResponse findByIdAndState(Long id, State state);
    TaskResponse update(Task task);
    TaskResponse assign(Task task);
    TaskResponse mapToResponse(Task task);
    List<TaskResponse> mapToResponse(List<Task> task);
}
