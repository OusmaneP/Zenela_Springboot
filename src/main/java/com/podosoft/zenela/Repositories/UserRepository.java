package com.podosoft.zenela.Repositories;

import com.podosoft.zenela.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.cover = :cover where u.id = :userId")
    int updateCoverPhoto(@Param("cover") String fileCode, @Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.profile = :profile, u.profileThumb = :profileThumb where u.id = :userId")
    int updateProfilePhoto(@Param("profile") String fileCode, @Param("userId") Long userId, @Param("profileThumb") String thumbCode);

    @Transactional
    @Query(value = "SELECT * FROM users WHERE id NOT IN :principalId ORDER BY RAND() LIMIT 200", nativeQuery = true)
    Collection<User> findAllRandom(@Param("principalId") List<Long> principalId);

    @Transactional
    @Query("SELECT u FROM User u WHERE u.firstName LIKE CONCAT('%', ?1, '%') OR u.lastName LIKE CONCAT('%', ?1, '%')")
    Collection<User> searchName(String name);
}
