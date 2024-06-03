package com.zawadzkia.springtodo.task.status;

import com.zawadzkia.springtodo.task.TaskDTO;
import com.zawadzkia.springtodo.task.TaskService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/task/status")
@RequiredArgsConstructor
public class TaskStatusController {

    private final TaskStatusService taskStatusService;
    private final TaskService taskService;

    @GetMapping
    String getStatusList(Model model) {
        List<TaskStatusDTO> userTaskStatusList = taskStatusService.getUserTaskStatusList();
        model.addAttribute("statuses", userTaskStatusList);
        return "status/list";
    }

    @PostMapping(value = "/{id}")
    String updateTask(@PathVariable Long id, @ModelAttribute("status") TaskStatusDTO taskStatusDTO) {
        TaskDTO taskDTO = taskService.getTaskDTOById(id);
        taskDTO.setStatus(taskStatusDTO);
        taskService.update(taskDTO);
        return "redirect:/task";
    }

    @GetMapping({ "/create" })
    String createTask() {
        return "status/create";
    }

    @PostMapping({ "/create" })
    String createTask(TaskStatusDTO statusDTO, Model model) {
        System.out.println(statusDTO.getId() + " " + statusDTO.getName() + " " + statusDTO.getDisplayName());

        if (statusDTO.getName() == "")
            return "redirect:/task/status/create";

        taskStatusService.create(statusDTO);
        return "redirect:/task/status";
    }
}
