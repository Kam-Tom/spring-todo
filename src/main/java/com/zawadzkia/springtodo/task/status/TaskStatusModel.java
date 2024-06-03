package com.zawadzkia.springtodo.task.status;

import com.zawadzkia.springtodo.user.UserModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "task_statuses")
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uc_taskstatusmodel_name", columnNames = { "name", "user_model_id" })
})
public class TaskStatusModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String displayName;

    @ManyToOne
    @JoinColumn(name = "user_model_id")
    private UserModel owner;

}
