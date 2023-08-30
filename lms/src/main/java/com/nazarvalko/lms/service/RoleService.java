package com.nazarvalko.lms.service;

import com.nazarvalko.lms.entity.Role;

public interface RoleService {
    Role findRoleByName(String name);
}
