package com.podosoft.zenela.Websocket.Repository;

import com.podosoft.zenela.Websocket.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Transactional
    @Query(value = "SELECT * FROM messages m WHERE m.sender = :principalId AND m.receiver = :user2 AND m.owner = :principalId OR m.receiver = :principalId AND m.sender = :user2 AND m.owner = :principalId  ORDER BY id ASC", nativeQuery = true)
    Collection<Message> getMyMessages(@Param("principalId") Long principalId, @Param("user2") Long user2);

    // Get MissedMessages
    @Transactional
    @Query("SELECT count(m) FROM Message m WHERE m.owner = :principalId AND m.status = false ")
    Integer getMissedMessages(@Param("principalId") Long principalId);

    @Transactional
    @Modifying
    @Query("UPDATE Message m SET m.status = TRUE WHERE m.sender = :sender AND m.receiver = :receiver AND m.owner = :receiver AND m.status = FALSE")
    int setMessageSeen(@Param("sender") Long sender, @Param("receiver") Long receiver);

    Collection<Message> findAllByReceiverAndSenderAndOwnerAndStatus(Long receiver, Long sender, Long owner, boolean status);

    Collection<Message> findAllByOwnerOrderByIdDesc(Long owner);
}
