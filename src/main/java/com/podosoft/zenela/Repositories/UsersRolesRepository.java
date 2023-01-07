package com.podosoft.zenela.Repositories;

import com.podosoft.zenela.Models.UsersRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRolesRepository extends JpaRepository<UsersRoles, Long> {
}
