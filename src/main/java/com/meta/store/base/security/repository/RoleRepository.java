package com.meta.store.base.security.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.base.security.entity.Role;

public interface RoleRepository extends BaseRepository<Role, Long> {

	Optional<Role> findByName(String name);
	//@Query(value = "select art from Article art join art.department dept where dept.id = :deptId")
    @Query(value = "SELECT r.* FROM sec_role r " +
            "INNER JOIN user_roles ur ON r.id = ur.role_id " +
            "INNER JOIN sec_user u ON ur.user_id = u.id " +
            "WHERE u.id = :userId", nativeQuery = true)
    Set<Role> findRolesByUserId(@Param("userId") Long userId);

}
