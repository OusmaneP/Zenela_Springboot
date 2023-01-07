package com.podosoft.zenela.Repositories;

import com.podosoft.zenela.Models.PostNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Collection;

public interface PostNotificationRepository extends JpaRepository<PostNotification, Long> {

    @Transactional
    @Query(value = "SELECT * FROM post_notifications WHERE receiver_id = :receiverId ORDER BY id DESC LIMIT 50", nativeQuery = true)
    Collection<PostNotification> findAllByReceiverIdOrderByIdDesc(@Param("receiverId") Long receiverId);

    @Transactional
    @Query(value = "SELECT COUNT(*) FROM post_notifications WHERE receiver_id = :receiverId AND has_read = :hasRead", nativeQuery = true)
    int findAllByReceiverIdAndHasReadOrderByIdDesc(@Param("receiverId") Long receiverId, @Param("hasRead") boolean hasRead);

    @Transactional
    @Modifying
    @Query("UPDATE PostNotification p SET p.hasRead = :hasRead where p.receiverId = :receiverId")
    void readPostNotifications(@Param("receiverId") Long receiverId, @Param("hasRead") boolean hasRead);

}
