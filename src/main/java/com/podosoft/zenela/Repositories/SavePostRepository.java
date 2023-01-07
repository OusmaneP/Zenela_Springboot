package com.podosoft.zenela.Repositories;

import com.podosoft.zenela.Models.SavePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public interface SavePostRepository extends JpaRepository<SavePost, Long> {

    Collection<SavePost> findAllBySaverId(Long saverId);

    @Transactional
    @Modifying
    void deleteBySaverIdAndPostId(Long saverId, Long postId);

}
