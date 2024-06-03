package com.zawadzkia.springtodo.task.status;

import com.zawadzkia.springtodo.task.TaskDTO;
import com.zawadzkia.springtodo.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/task/status")
@RequiredArgsConstructor
public class TaskStatusController {

    private final TaskService taskService;

    @PostMapping(value = "/{id}")
    String updateTask(@PathVariable Long id, @ModelAttribute("status") TaskStatusDTO taskStatusDTO) {
        TaskDTO taskDTO = taskService.getTaskDTOById(id);
        taskDTO.setStatus(taskStatusDTO);
        taskService.update(taskDTO);
        return "redirect:/task";
    }
}
