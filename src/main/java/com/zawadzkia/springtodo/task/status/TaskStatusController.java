package com.zawadzkia.springtodo.task.status;

import com.zawadzkia.springtodo.exception.ElementExistsException;
import com.zawadzkia.springtodo.exception.ResourceNotEmptyException;
import com.zawadzkia.springtodo.exception.UnauthorizedAccessException;
import com.zawadzkia.springtodo.task.TaskDTO;
import com.zawadzkia.springtodo.task.TaskService;
import com.zawadzkia.springtodo.task.category.TaskCategoryDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping(params = "search")
    String getStatusListWithSearch(Model model, @RequestParam("search") String search) {
        List<TaskStatusDTO> userTaskStatusList = taskStatusService.getUserTaskStatusList();
        String lowerCaseSearch = search.toLowerCase();
        userTaskStatusList = userTaskStatusList.stream()
                .filter(status -> status.getName().toLowerCase().contains(lowerCaseSearch) ||
                        (status.getDisplayName() != null
                                && status.getDisplayName().toLowerCase().contains(lowerCaseSearch)))
                .collect(Collectors.toList());
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
    String createTask(Model model) {
        model.addAttribute("taskStatusDTO", new TaskStatusDTO());
        return "status/create";
    }

    @PostMapping({ "/create" })
    String createTask(@Valid @ModelAttribute TaskStatusDTO taskStatusDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "status/create";
        }

        try {
            taskStatusService.create(taskStatusDTO);
        } catch (ElementExistsException e) {
            bindingResult.rejectValue("name", "error.name", e.getMessage());
            return "status/create";
        }
        return "redirect:/task/status";
    }

    @GetMapping(value = "update/{id}")
    String updateStatus(@PathVariable Long id, Model model) {

        TaskStatusDTO status = taskStatusService.getStatus(id);
        model.addAttribute("status", status);

        return "status/update";
    }

    @PostMapping(value = "update/{id}")
    String updateStatus(@PathVariable Long id, @ModelAttribute TaskStatusDTO statusDTO) {
        taskStatusService.updateStatus(statusDTO);

        return "redirect:/task/status";
    }

    @PostMapping(value = "/delete/{id}")
    String deletStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        try {
            taskStatusService.deleteStatus(id);
        } catch (ResourceNotEmptyException e) {
            redirectAttributes.addFlashAttribute("deleteError", e.getMessage());
            return "redirect:/task/status";
        }

        return "redirect:/task/status";
    }
}
