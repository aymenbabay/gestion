package com.meta.store.base.security.repository;

import java.util.List;
import java.util.Optional;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.base.security.entity.AppUser;

public interface AppUserRepository extends BaseRepository<AppUser, Long> {

	Optional<AppUser> findByUserName(String userName);

	Optional<AppUser> findByEmail(String email);

	boolean existsByUserName(String username);


}
