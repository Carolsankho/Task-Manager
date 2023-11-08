package com.manage.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manage.task.model.Task;
import com.manage.task.model.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByOwnerOrderByDateDesc(User user);
}