package com.manage.task.service;

import java.util.List;

import com.manage.task.model.Role;

public interface RoleService {
    Role createRole(Role role);

    List<Role> findAll();
}
