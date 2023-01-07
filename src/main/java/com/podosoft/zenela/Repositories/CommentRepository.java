package com.podosoft.zenela.Repositories;

import com.podosoft.zenela.Models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Transactional
    @Query(value = "SELECT * FROM comments WHERE post = :post ORDER BY id DESC LIMIT 40", nativeQuery = true)
    Collection<Comment> findAllRecentPost(@Param("post") Long post);

    Collection<Comment> findAllByPostOrderByIdDesc(@Param("post") Long post);

    @Transactional
    @Query(value = "SELECT COUNT(*) FROM comments WHERE post = :post", nativeQuery = true)
    long findCommentSize(@Param("post") Long post);
}
