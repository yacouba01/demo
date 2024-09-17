package com.malinov.demo.services.task;

import com.malinov.demo.dto.TaskResponse;
import com.malinov.demo.dto.UsersResponse;
import com.malinov.demo.enums.State;
import com.malinov.demo.enums.TaskType;
import com.malinov.demo.exceptions.ResourceNotFoundException;
import com.malinov.demo.models.Task;
import com.malinov.demo.models.Users;
import com.malinov.demo.repositories.TaskRepository;
import com.malinov.demo.services.authentication.AuthenticationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final AuthenticationService authenticationService;

    @Override
    public TaskResponse save(Task task) {
        Users users = authenticationService.getAuthor();
        task.setAssignTo(users);
        task.setCreatedBy(users);
        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public List<TaskResponse> findByState(State state) {
        List<Task> tasks = taskRepository.findByState(state);
        tasks.sort(Comparator.comparing(Task::getCreatedAt));
        return mapToResponse(tasks);
    }

    @Override
    public List<TaskResponse> findByAuthAndState(State state) {
        Users auth = authenticationService.getAuthor();
        List<Task> tasks = taskRepository.findByCreatedByIdOrAssignToIdAndState(auth.getId(), auth.getId(), State.ACTIVATED);
        tasks.sort(Comparator.comparing(Task::getCreatedAt));
        return mapToResponse(tasks);
    }

    @Override
    public List<TaskResponse> findByAuthAndTypeAndState(TaskType type, State state) {
        Users auth = authenticationService.getAuthor();
        List<Task> tasks = taskRepository.findByCreatedByIdOrAssignToIdAndTypeAndState(auth.getId(), auth.getId(), type, State.ACTIVATED);
        tasks.sort(Comparator.comparing(Task::getCreatedAt));
        return mapToResponse(tasks);
    }

    @Override
    public TaskResponse findByIdAndState(Long id, State state) {
        Task task = taskRepository.findByIdAndState(id, state);
        if (task == null) {
            throw new ResourceNotFoundException("Cette tâche n'existe pas");
        }
        return mapToResponse(task);
    }

    @Override
    public TaskResponse update(Task task) {
        Task taskToUpdate = taskRepository.findByIdAndState(task.getId(), State.ACTIVATED);
        if (taskToUpdate == null) {
            throw new ResourceNotFoundException("Cette tâche n'existe pas");
        }
        taskToUpdate.setName(task.getName());
        taskToUpdate.setType(task.getType());
        taskToUpdate.setState(task.getState());
        return mapToResponse(taskToUpdate);
    }

    @Override
    public TaskResponse assign(Task task) {
        Task taskToAssign = taskRepository.findByIdAndState(task.getId(), State.ACTIVATED);
        if (taskToAssign == null) {
            throw new ResourceNotFoundException("Cette tâche n'existe pas");
        }
        taskToAssign.setAssignTo(task.getAssignTo());
        return mapToResponse(taskToAssign);
    }

    @Override
    public TaskResponse mapToResponse(Task task) {
        UsersResponse assignTo = null;
        if (task.getAssignTo() != null) {
            assignTo = UsersResponse.builder()
                    .firstName(task.getAssignTo().getFirstName())
                    .lastName(task.getAssignTo().getLastName())
                    .email(task.getAssignTo().getEmail())
                    .phoneNumber(task.getAssignTo().getPhoneNumber())
                    .state(task.getAssignTo().getState())
                    .build();
        }

        UsersResponse createdBy = null;
        if (task.getCreatedBy() != null) {
            createdBy = UsersResponse.builder()
                    .firstName(task.getCreatedBy().getFirstName())
                    .lastName(task.getCreatedBy().getLastName())
                    .email(task.getCreatedBy().getEmail())
                    .phoneNumber(task.getCreatedBy().getPhoneNumber())
                    .state(task.getCreatedBy().getState())
                    .build();
        }
        return TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .createdAt(task.getCreatedAt())
                .assignTo(assignTo)
                .createdBy(createdBy)
                .type(task.getType())
                .state(task.getState())
                .build();
    }

    @Override
    public List<TaskResponse> mapToResponse(List<Task> tasksList) {
        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task task : tasksList) {
            taskResponses.add(mapToResponse(task));
        }
        return taskResponses;
    }
}
