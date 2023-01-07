package com.podosoft.zenela.Repositories;

import com.podosoft.zenela.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    @Transactional
    @Modifying
    @Query(value = "select * from roles, users_roles where roles.id = users_roles.role_id and users_roles.user_id = ?1", nativeQuery = true)
    Collection<Role> findRolesOfUser(Long userId);
}
