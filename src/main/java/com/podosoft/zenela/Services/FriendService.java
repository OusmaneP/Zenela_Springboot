package com.podosoft.zenela.Services;

import com.podosoft.zenela.Models.Friend;

public interface FriendService {
    Friend saveRequest(Long principalId, Long id_b);

    int acceptFriendRequest(Long principalId, Long userId);

    int cancelFriend(Long principalId, Long userId);
}
