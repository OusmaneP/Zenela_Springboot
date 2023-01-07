package com.podosoft.zenela.Dto;

public class LikeDto {

    private Long postId;

    public LikeDto() {
    }

    public LikeDto(Long postId) {
        this.postId = postId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
