package com.zawadzkia.springtodo.task.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zawadzkia.springtodo.task.TaskDTO;
import com.zawadzkia.springtodo.task.TaskService;
import com.zawadzkia.springtodo.task.status.TaskStatusDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/task/category")
@RequiredArgsConstructor
public class TaskCategoryController {

    private final TaskCategoryService taskCategoryService;
    private final TaskService taskService;

    @GetMapping
    String getCategoryList(Model model) {
        List<TaskCategoryDTO> userTaskCategoryList = taskCategoryService.getUserTaskCategoryList();
        model.addAttribute("categories", userTaskCategoryList);
        return "category/list";
    }
    @GetMapping(params = "search")
    String getCategoryListWithSearch(Model model, @RequestParam("search") String search) {
        List<TaskCategoryDTO> userTaskCategoryList = taskCategoryService.getUserTaskCategoryList();
        String lowerCaseSearch = search.toLowerCase();
        userTaskCategoryList = userTaskCategoryList.stream()
            .filter(category -> category.getName().toLowerCase().contains(lowerCaseSearch) || 
            (category.getDescription() != null && category.getDescription().toLowerCase().contains(lowerCaseSearch)))
            .collect(Collectors.toList());

        model.addAttribute("categories", userTaskCategoryList);
        return "category/list";
    }
    @PostMapping(value = "/{id}")
    String updateCategory(@PathVariable Long id, @ModelAttribute("category") TaskCategoryDTO taskCategoryDTO) {
        TaskDTO taskDTO = taskService.getTaskDTOById(id);
        taskDTO.setCategory(taskCategoryDTO);
        taskService.update(taskDTO);
        return "redirect:/task";
    }

    @GetMapping({ "/create" })
    String createTask() {
        return "category/create";
    }

    @PostMapping({ "/create" })
    String createTask(@ModelAttribute TaskCategoryDTO categoryDTO, Model model) {
        System.out.println(categoryDTO.getName());

        if (categoryDTO.getName() == "")
            return "redirect:/task/category/create";

        taskCategoryService.create(categoryDTO);
        return "redirect:/task/category";
    }
}
