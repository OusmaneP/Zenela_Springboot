package com.podosoft.zenela.Repositories;

import com.podosoft.zenela.Models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Collection<Like> findAllByPost(Long postId);

    @Transactional
    @Modifying
    void deleteByLikerAndPost(Long liker, Long post);

    Optional<Like> findByLikerAndPost(Long liker, Long post);

    @Transactional
    @Query(value = "SELECT COUNT(*) FROM likes WHERE post = :post", nativeQuery = true)
    Long findLikeSize(@Param("post") Long post);

}
