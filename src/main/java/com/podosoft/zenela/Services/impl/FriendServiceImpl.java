package com.podosoft.zenela.Services.impl;

import com.podosoft.zenela.Models.Friend;
import com.podosoft.zenela.Repositories.FriendRepository;
import com.podosoft.zenela.Services.FriendService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;

    public FriendServiceImpl(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    @Override
    public Friend saveRequest(Long principalId, Long receiver) {
        Friend friend = new Friend(principalId, receiver, "request", new Date());
        return friendRepository.save(friend);
    }

    @Override
    public int acceptFriendRequest(Long principalId, Long sender) {
        return friendRepository.acceptFriendReq(principalId, sender, "friends");
    }

    @Override
    public int cancelFriend(Long principalId, Long userId) {
        return friendRepository.cancelFriend(principalId, userId);
    }
}
