package com.zawadzkia.springtodo.task.category;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TaskCategoryDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String image;
}
