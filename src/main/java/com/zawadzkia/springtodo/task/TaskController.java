package com.zawadzkia.springtodo.task;

import com.zawadzkia.springtodo.task.category.TaskCategoryDTO;
import com.zawadzkia.springtodo.task.category.TaskCategoryService;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping({ "/task" })
@Slf4j
class TaskController {

    private final TaskService taskService;

    private final TaskStatusService taskStatusService;

    private final TaskCategoryService taskCategoryService;

    @GetMapping
    String getTaskList(Model model) {
        return getTaskListCommon(model, null);
    }

    @GetMapping(params = "search")
    String getTaskListWithSearch(Model model, @RequestParam("search") String search) {
        return getTaskListCommon(model, search);
    }

    private String getTaskListCommon(Model model, String search) {
        List<TaskDTO> taskList = (search == null) ? taskService.getTaskList() : taskService.getTaskList(search);
        List<TaskStatusDTO> userTaskStatusList = taskStatusService.getUserTaskStatusList();
        List<TaskCategoryDTO> userTaskCategoryList = taskCategoryService.getUserTaskCategoryList();
        model.addAttribute("tasks", taskList);
        model.addAttribute("statusList", userTaskStatusList);
        model.addAttribute("categoryList", userTaskCategoryList);
        return "task/list";
    }

    @GetMapping({ "/create" })
    String createTask(Model model) {
        List<TaskStatusDTO> userTaskStatusList = taskStatusService.getUserTaskStatusList();
        List<TaskCategoryDTO> userTaskCategoryList = taskCategoryService.getUserTaskCategoryList();
        model.addAttribute("statusList", userTaskStatusList);
        model.addAttribute("categoryList", userTaskCategoryList);

        return "task/create";
    }

    @PostMapping({ "/create" })
    String createTask(TaskDTO taskDTO, @RequestParam Long statusId, @RequestParam Long categoryId, Model model) {
        TaskStatusDTO statusDTO = taskStatusService.findStatusDTO(statusId);
        TaskCategoryDTO categoryDTO = taskCategoryService.finCategoryDTO(categoryId);

        System.out.println(
                taskDTO.getId() + " " + taskDTO.getSummary() + " " + taskDTO.getDescription() + " "
                        + categoryDTO.getName()
                        + " " + statusDTO.getDisplayName());

        taskDTO.setStatus(statusDTO);
        taskDTO.setCategory(categoryDTO);

        taskService.create(taskDTO);
        return "redirect:/task";
    }
}
