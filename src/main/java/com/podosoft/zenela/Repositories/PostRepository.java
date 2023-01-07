package com.podosoft.zenela.Repositories;

import com.podosoft.zenela.Models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Transactional
    @Query("SELECT p FROM Post p ORDER BY p.id DESC")
    Collection<Post> findAllDesc();

    Collection<Post> findAllByPosterIdOrderByIdDesc(Long posterId);

    @Transactional
    @Query(value = "SELECT * FROM posts WHERE type LIKE %:type% ORDER BY RAND() LIMIT 40", nativeQuery = true)
    Collection<Post> findRandomTypeLimit(@Param("type") String type);

    @Transactional
    @Modifying
    @Query("UPDATE Post p SET p.notified = :notified where p.id = :postId")
    void notifyPost(@Param("postId") Long postId, @Param("notified") boolean notified);

    @Transactional
    @Modifying
    @Query("UPDATE Post p SET p.notified = :notified where p.posterId = :posterId")
    void unNotifyPost(@Param("posterId") Long posterId, @Param("notified") boolean notified);

    @Transactional
    @Modifying
    @Query("UPDATE Post p SET p.comment = :comment where p.id = :postId")
    void updatePostComment(@Param("postId") Long postId, @Param("comment") String comment);

    @Transactional
    @Query(value = "SELECT * FROM posts WHERE type LIKE %:type% AND poster_id = :posterId", nativeQuery = true)
    Collection<Post> findMyPostVideoTypeLimit(@Param("type") String type, @Param("posterId") Long posterId);

}
