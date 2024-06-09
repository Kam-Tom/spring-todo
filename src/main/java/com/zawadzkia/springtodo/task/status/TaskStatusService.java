package com.zawadzkia.springtodo.task.status;

import com.zawadzkia.springtodo.auth.AppUserDetails;
import com.zawadzkia.springtodo.exception.ResourceNotEmptyException;
import com.zawadzkia.springtodo.exception.UnauthorizedAccessException;
import com.zawadzkia.springtodo.task.TaskModel;
import com.zawadzkia.springtodo.task.TaskRepository;
import com.zawadzkia.springtodo.task.TaskService;
import com.zawadzkia.springtodo.task.category.TaskCategoryDTO;
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
    private final TaskRepository taskRepository;

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

    public void deleteStatus(Long id) {

        AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TaskStatusModel status = taskStatusRepository.findById(id).orElseThrow();

        if (!status.getOwner().equals(userDetails.getUser())) {
            throw new UnauthorizedAccessException("This status does not belong to the owner");
        }

        List<TaskModel> tasks = taskRepository.findAllByStatus(status);
        if(!tasks.isEmpty()) {
            throw new ResourceNotEmptyException("Status is not empty");
        }

        taskStatusRepository.delete(status);
    }

    public void updateStatus(TaskStatusDTO statusDTO) {
        AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TaskStatusModel status = taskStatusRepository.findById(statusDTO.getId()).orElseThrow();
        if (!status.getOwner().equals(userDetails.getUser())) {
            throw new UnauthorizedAccessException("This category does not belong to the owner");
        }

        status.setName(statusDTO.getName());
        status.setDisplayName(statusDTO.getDisplayName());
        
        taskStatusRepository.save(status);
    }

    public TaskStatusDTO getStatus(Long id) {
        AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TaskStatusModel statusModel = taskStatusRepository.findById(id).orElseThrow();

        if (!statusModel.getOwner().equals(userDetails.getUser())) {
            throw new UnauthorizedAccessException("This status does not belong to the owner");
        }

        return new TaskStatusDTO(statusModel.getId(), statusModel.getName(), statusModel.getDisplayName());
    }

}
