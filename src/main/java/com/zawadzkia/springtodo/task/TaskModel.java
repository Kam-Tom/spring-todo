package com.zawadzkia.springtodo.task;

import com.zawadzkia.springtodo.task.category.TaskCategoryModel;
import com.zawadzkia.springtodo.task.status.TaskStatusModel;
import com.zawadzkia.springtodo.user.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "tasks")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private LocalDateTime startDate;

    @Column(nullable = true)
    private LocalDateTime dueDate;

    @Column(nullable = true)
    private String attachment;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private TaskCategoryModel category;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private TaskStatusModel status;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserModel owner;

    @Override
    public String toString() {
        return "TaskModel{" +
                "id=" + id +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", dueDate=" + dueDate +
                ", attachment='" + attachment + '\'' +
                ", category=" + category +
                ", owner=" + owner +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof TaskModel taskModel))
            return false;

        if (getId() != null ? !getId().equals(taskModel.getId()) : taskModel.getId() != null)
            return false;
        if (getSummary() != null ? !getSummary().equals(taskModel.getSummary()) : taskModel.getSummary() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(taskModel.getDescription())
                : taskModel.getDescription() != null)
            return false;
        if (getStartDate() != null ? !getStartDate().equals(taskModel.getStartDate())
                : taskModel.getStartDate() != null)
            return false;
        if (getDueDate() != null ? !getDueDate().equals(taskModel.getDueDate()) : taskModel.getDueDate() != null)
            return false;
        if (getAttachment() != null ? !getAttachment().equals(taskModel.getAttachment())
                : taskModel.getAttachment() != null)
            return false;
        if (getCategory() != null ? !getCategory().equals(taskModel.getCategory()) : taskModel.getCategory() != null)
            return false;
        return getOwner() != null ? getOwner().equals(taskModel.getOwner()) : taskModel.getOwner() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getSummary() != null ? getSummary().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getDueDate() != null ? getDueDate().hashCode() : 0);
        result = 31 * result + (getAttachment() != null ? getAttachment().hashCode() : 0);
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        result = 31 * result + (getOwner() != null ? getOwner().hashCode() : 0);
        return result;
    }
}
