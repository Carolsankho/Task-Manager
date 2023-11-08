package com.manage.task.service;

import java.util.List;

import com.manage.task.model.Task;
import com.manage.task.model.User;

public interface TaskService {

    void createTask(Task task);

    void updateTask(Long id, Task task);

    void deleteTask(Long id);

    List<Task> findAll();

    List<Task> findByOwnerOrderByDateDesc(User user);

    void setTaskCompleted(Long id);

    void setTaskNotCompleted(Long id);

    List<Task> findFreeTasks();

    Task getTaskById(Long taskId);

    void assignTaskToUser(Task task, User user);

    void unassignTask(Task task);
}
