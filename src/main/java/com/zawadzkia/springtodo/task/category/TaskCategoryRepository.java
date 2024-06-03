package com.zawadzkia.springtodo.task.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zawadzkia.springtodo.user.UserModel;

@Repository
public interface TaskCategoryRepository extends JpaRepository<TaskCategoryModel, Long> {
    List<TaskCategoryModel> findAllByOwner(UserModel owner);
}
