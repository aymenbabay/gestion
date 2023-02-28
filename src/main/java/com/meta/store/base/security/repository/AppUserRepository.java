package com.meta.store.base.security.repository;

import java.util.Optional;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.base.security.entity.AppUser;

public interface AppUserRepository extends BaseRepository<AppUser, Long> {

	Optional<AppUser> findByUserName(String userName);

}
