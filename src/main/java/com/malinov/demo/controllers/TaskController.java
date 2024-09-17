package com.malinov.demo.controllers;

import com.malinov.demo.dto.TaskResponse;
import com.malinov.demo.enums.State;
import com.malinov.demo.enums.TaskType;
import com.malinov.demo.models.Task;
import com.malinov.demo.services.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse save(@RequestBody Task task) {
        return taskService.save(task);
    }

    @GetMapping("/state/{state}")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskResponse> findByState(@PathVariable State state) {
        return taskService.findByState(state);
    }

    @GetMapping("/auth/state/{state}")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskResponse> findByAuthAndState(@PathVariable State state) {
        return taskService.findByAuthAndState(state);
    }

    @GetMapping("/auth/type/{type}/state/{state}")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskResponse> findByAuthAndTypeAndState(@PathVariable TaskType type, @PathVariable State state) {
        return taskService.findByAuthAndTypeAndState(type, state);
    }

    @GetMapping("/{id}/state/{state}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse findByIdAndState(@PathVariable Long id, @PathVariable State state) {
        return taskService.findByIdAndState(id, state);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse update(@RequestBody Task task) {
        return taskService.update(task);
    }

    @PutMapping("/assign")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse assign(@RequestBody Task task) {
        return taskService.assign(task);
    }
}
