package com.malinov.demo.dto;

import com.malinov.demo.enums.State;
import com.malinov.demo.enums.TaskType;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class TaskResponse {

    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private UsersResponse createdBy;
    private UsersResponse assignTo;
    private State state;
    private TaskType type;
}
