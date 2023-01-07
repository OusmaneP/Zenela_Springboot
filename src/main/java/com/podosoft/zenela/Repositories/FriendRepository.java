package com.podosoft.zenela.Repositories;

import com.podosoft.zenela.Models.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    // requests
    @Transactional
    @Query(value = "SELECT f.sender FROM friends f WHERE f.receiver = :principalId AND f.status = :status ORDER BY f.id DESC LIMIT 300", nativeQuery = true)
    Collection<Long> findAllByReceiverAndStatus(@Param("principalId") Long idA, @Param("status") String status);

    // invited
    @Transactional
    @Query(value = "SELECT f.receiver FROM friends f WHERE f.sender = :principalId AND f.status = :status ORDER BY f.id DESC LIMIT 300", nativeQuery = true)
    Collection<Long> findAllBySenderAndStatusOrderByIdDesc(@Param("principalId") Long idA, @Param("status") String status);

    @Transactional
    @Query(value = "SELECT * FROM friends f WHERE f.sender = :principalId OR f.receiver = :principalId", nativeQuery = true)
    Collection<Friend> findAllFriendShips(@Param("principalId") Long principalId);

    @Transactional
    @Modifying
    @Query("UPDATE Friend f SET f.status = :status where f.sender = :sender AND f.receiver = :principalId")
    int acceptFriendReq(@Param("principalId") Long principalId, @Param("sender") Long sender,@Param("status") String friends);

    @Transactional
    @Query(value = "SELECT * FROM friends f WHERE f.sender = :principalId AND f.status = :status OR f.receiver = :principalId AND f.status = :status ORDER BY RAND()", nativeQuery = true)
    Collection<Friend> findMyFriends(@Param("principalId") Long principalId, @Param("status") String friends);

    @Transactional
    @Modifying
    @Query("DELETE FROM Friend f WHERE f.sender = :principalId AND f.receiver = :userId OR f.sender = :userId AND f.receiver = :principalId")
    int cancelFriend(Long principalId, Long userId);
}
