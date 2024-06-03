package com.zawadzkia.springtodo.task.status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TaskStatusDTO implements Serializable {

    private Long id;

    private String name;

    private String displayName;

}
