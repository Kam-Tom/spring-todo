package com.zawadzkia.springtodo.task;

import com.zawadzkia.springtodo.task.status.TaskStatusDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TaskDTO implements Serializable {
    private Long id;

    private String summary;

    private String description;

    private LocalDateTime startDate;

    private LocalDateTime dueDate;

    private String attachment;

    private TaskStatusDTO status;

}
