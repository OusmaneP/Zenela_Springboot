package com.podosoft.zenela.Dto;

public class FriendDto {
    private Long userId;

    public FriendDto() {
    }

    public FriendDto(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
