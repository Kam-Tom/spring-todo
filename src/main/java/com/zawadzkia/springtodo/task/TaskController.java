package com.zawadzkia.springtodo.task;

import com.zawadzkia.springtodo.task.status.TaskStatusDTO;
import com.zawadzkia.springtodo.task.status.TaskStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping({ "/task" })
@Slf4j
class TaskController {

    private final TaskService taskService;

    private final TaskStatusService taskStatusService;

    @GetMapping
    String getTaskList(Model model) {
        List<TaskDTO> taskList = taskService.getTaskList();
        List<TaskStatusDTO> userTaskStatusList = taskStatusService.getUserTaskStatusList();
        model.addAttribute("tasks", taskList);
        model.addAttribute("statusList", userTaskStatusList);
        return "task/list";

    }


}
