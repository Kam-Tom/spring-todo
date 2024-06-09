package com.zawadzkia.springtodo.task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zawadzkia.springtodo.task.category.TaskCategoryModel;
import com.zawadzkia.springtodo.task.status.TaskStatusModel;
import com.zawadzkia.springtodo.user.UserModel;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Long> {
    List<TaskModel> findAllByOwnerOrderById(UserModel owner);
    List<TaskModel> findAllByCategory(TaskCategoryModel category);
    List<TaskModel> findAllByStatus(TaskStatusModel status);
}
