package com.zawadzkia.springtodo.task.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zawadzkia.springtodo.auth.AppUserDetails;
import com.zawadzkia.springtodo.exception.ElementExistsException;
import com.zawadzkia.springtodo.exception.ResourceNotEmptyException;
import com.zawadzkia.springtodo.exception.UnauthorizedAccessException;
import com.zawadzkia.springtodo.exception.UsernameAlreadyTakenException;
import com.zawadzkia.springtodo.task.TaskModel;
import com.zawadzkia.springtodo.task.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskCategoryService {

    private final TaskCategoryRepository taskCategoryRepository;
    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public List<TaskCategoryDTO> getUserTaskCategoryList() {
        ArrayList<TaskCategoryDTO> result = new ArrayList<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AppUserDetails userDetails) {
            List<TaskCategoryModel> allByOwner = taskCategoryRepository.findAllByOwner(userDetails.getUser());
            allByOwner.forEach(category -> result.add(new TaskCategoryDTO(category.getId(), category.getName(),
                    category.getDescription(), category.getImage())));
        }
        return result;
    }

    @Transactional
    public void create(TaskCategoryDTO taskCategoryDTO) {

        if (taskCategoryRepository.findByName(taskCategoryDTO.getName()) != null) {
            throw new ElementExistsException("category");
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AppUserDetails userDetails) {
            TaskCategoryModel categoryModel = new TaskCategoryModel(null, taskCategoryDTO.getName(),
                    taskCategoryDTO.getDescription(), taskCategoryDTO.getImage(), userDetails.getUser());
            taskCategoryRepository.save(categoryModel);
        }
    }

    public TaskCategoryDTO finCategoryDTO(Long id) {
        Optional<TaskCategoryModel> categoryModel = taskCategoryRepository.findById(id);
        return new TaskCategoryDTO(id, categoryModel.get().getName(), categoryModel.get().getDescription(),
                categoryModel.get().getImage());

    }

    public void deleteCategory(Long id) {

        AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        TaskCategoryModel category = taskCategoryRepository.findById(id).orElseThrow();

        if (!category.getOwner().equals(userDetails.getUser())) {
            throw new UnauthorizedAccessException("This category does not belong to the owner");
        }

        List<TaskModel> tasks = taskRepository.findAllByCategory(category);
        if (!tasks.isEmpty()) {
            throw new ResourceNotEmptyException("Category is not empty");
        }

        taskCategoryRepository.delete(category);
    }
}
