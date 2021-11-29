package com.fullstackproject.service.impl;

import com.fullstackproject.models.Role;
import com.fullstackproject.repositories.RoleRepository;
import com.fullstackproject.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedRolesInDb() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.save(new Role("ROLE_ADMIN"));
            this.roleRepository.save(new Role("ROLE_USER"));
            this.roleRepository.save(new Role("ROLE_ROOT"));
        }
    }

    @Override
    public Set<Role> findAllRoles() {
        return new HashSet<>(this.roleRepository.findAll());
    }
}
