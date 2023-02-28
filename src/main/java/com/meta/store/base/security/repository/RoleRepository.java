package com.meta.store.base.security.repository;

import java.util.Optional;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.base.security.entity.Role;

public interface RoleRepository extends BaseRepository<Role, Long> {

	Optional<Role> findByName(String name);

}
