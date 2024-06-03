package com.zawadzkia.springtodo.task.category;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zawadzkia.springtodo.auth.AppUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskCategoryService {

    private final TaskCategoryRepository taskCategoryRepository;

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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AppUserDetails userDetails) {
            TaskCategoryModel categoryModel = new TaskCategoryModel(null, taskCategoryDTO.getName(),
                    taskCategoryDTO.getDescription(), taskCategoryDTO.getImage(), userDetails.getUser());
            taskCategoryRepository.save(categoryModel);
        }
    }
}
