package com.zawadzkia.springtodo.task.status;

import com.zawadzkia.springtodo.auth.AppUserDetails;
import com.zawadzkia.springtodo.task.category.TaskCategoryModel;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskStatusService {

    private final TaskStatusRepository taskStatusRepository;

    public List<TaskStatusDTO> getUserTaskStatusList() {
        ArrayList<TaskStatusDTO> result = new ArrayList<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AppUserDetails userDetails) {
            List<TaskStatusModel> allByOwner = taskStatusRepository.findAllByOwner(userDetails.getUser());
            allByOwner.forEach(status -> result.add(new TaskStatusDTO(status.getId(), status.getName(),
                    status.getDisplayName())));
        }
        return result;
    }

    public void create(TaskStatusDTO statusDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AppUserDetails userDetails) {
            TaskStatusModel statusModel = new TaskStatusModel(null, statusDTO.getName(),
                    statusDTO.getDisplayName(), userDetails.getUser());
            taskStatusRepository.save(statusModel);
        }
    }

    public TaskStatusDTO findStatusDTO(Long statusId) {
        Optional<TaskStatusModel> statusModel = taskStatusRepository.findById(statusId);
        return new TaskStatusDTO(statusId, statusModel.get().getName(), statusModel.get().getDisplayName());
    }

}
