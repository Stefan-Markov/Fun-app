package com.fullstackproject.service;

import com.fullstackproject.models.Role;

import java.util.Set;

public interface RoleService {

    void seedRolesInDb();

    Set<Role> findAllRoles();

}
