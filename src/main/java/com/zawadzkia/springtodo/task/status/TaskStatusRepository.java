package com.zawadzkia.springtodo.task.status;

import com.zawadzkia.springtodo.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatusModel, Long> {
    List<TaskStatusModel> findAllByOwner(UserModel owner);

    TaskStatusModel findByName(String name);
}
