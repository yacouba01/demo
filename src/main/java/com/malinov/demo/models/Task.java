package com.malinov.demo.models;

import com.malinov.demo.enums.State;
import com.malinov.demo.enums.TaskType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@ToString
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne
    private Users createdBy;
    @ManyToOne
    private Users assignTo;
    @Enumerated(EnumType.STRING)
    private State state = State.ACTIVATED;
    @Enumerated(EnumType.STRING)
    private TaskType type = TaskType.IN_PROGRESS;
}
