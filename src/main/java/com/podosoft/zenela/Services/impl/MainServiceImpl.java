package com.podosoft.zenela.Services.impl;

import com.podosoft.zenela.Dto.Notifications;
import com.podosoft.zenela.Dto.Responses.SearchResponse;
import com.podosoft.zenela.Dto.Responses.UserResponse;
import com.podosoft.zenela.Models.Friend;
import com.podosoft.zenela.Models.User;
import com.podosoft.zenela.Repositories.FriendRepository;
import com.podosoft.zenela.Repositories.PostRepository;
import com.podosoft.zenela.Repositories.UserRepository;
import com.podosoft.zenela.Services.FileService;
import com.podosoft.zenela.Services.MainService;
import com.podosoft.zenela.Services.PostService;
import com.podosoft.zenela.Websocket.Repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Service
public class MainServiceImpl implements MainService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostService postService;
    private final FriendRepository friendRepository;
    private final MessageRepository messageRepository;
    private final FileService fileService;

    public MainServiceImpl(UserRepository userRepository, PostRepository postRepository, PostService postService, FriendRepository friendRepository, MessageRepository messageRepository, FileService fileService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.postService = postService;
        this.friendRepository = friendRepository;
        this.messageRepository = messageRepository;
        this.fileService = fileService;
    }

    @Override
    public User findPrincipal(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public String uploadCoverProfile(String fileName, MultipartFile multipartFile, String comment, Long posterId, String updateType) throws IOException {

        String fileCode = postService.uploadPost(fileName, multipartFile, comment, posterId);

        int update = 0;
        if (updateType.equals("cover")) {
            update = userRepository.updateCoverPhoto(fileCode, posterId);
        } else if (updateType.equals("profile")) {
            String thumbCode = fileService.createUploadThumbnail(multipartFile);
            System.out.println(thumbCode);
            update = userRepository.updateProfilePhoto(fileCode, posterId, thumbCode);
        }

        if (update == 1) {
            System.out.println("Updated successfully !");
            return fileCode;
        } else {
            System.out.println("Error updating cover or profile");
            return "";
        }


    }

    @Override
    public Collection<User> getRandomUsers(Long principalId, List<Long> friendRequestsIds) {
        friendRequestsIds.add(principalId);
        return userRepository.findAllRandom(friendRequestsIds);
    }


    // friend Requests
    @Override
    public Collection<User> getFriendRequests(Long principalId) {
        Collection<Long> friendRequestsIds = friendRepository.findAllByReceiverAndStatus(principalId, "request");

        return userRepository.findAllById(friendRequestsIds);
    }

    // Invited Friends
    @Override
    public Collection<User> getInvitedFriends(Long principalId) {
        Collection<Long> invitedFriendsIds = friendRepository.findAllBySenderAndStatusOrderByIdDesc(principalId, "request");

        return userRepository.findAllById(invitedFriendsIds);
    }

    // Friendships
    @Override
    public Collection<User> getFriendShips(Long principalId) {
        Collection<Friend> friendShips = friendRepository.findAllFriendShips(principalId);
        List<Long> friendShipsIds = new ArrayList<>();

        for (Friend friendShip:
             friendShips) {
            if(!Objects.equals(friendShip.getSender(), principalId)){
                friendShipsIds.add(friendShip.getSender());
            }
            else{
                friendShipsIds.add(friendShip.getReceiver());
            }

        }

        return userRepository.findAllById(friendShipsIds);
    }

    // my friends
    @Override
    public Collection<User> getMyFriends(Long principalId) {
        Collection<Friend> myFriends = friendRepository.findMyFriends(principalId, "friends");
        List<Long> myFriendsIds = new ArrayList<>();

        // retrieve their ids
        for (Friend myFriend:
                myFriends) {
            if(!Objects.equals(myFriend.getSender(), principalId)){
                myFriendsIds.add(myFriend.getSender());
            }
            else {
                myFriendsIds.add(myFriend.getReceiver());
            }
        }

        return userRepository.findAllById(myFriendsIds);
    }

    // User by id
    @Override
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    // search People
    @Override
    public SearchResponse searchPeople(String query) {
        Collection<User> users = userRepository.searchName(query);

        SearchResponse searchResponse = new SearchResponse(new Date(), query);

        for (User user:
             users) {
            UserResponse userResponse = new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getProfile(), "/view_profile/" + user.getId());
            searchResponse.getResults().add(userResponse);
        }

        return searchResponse;
    }

    // Notification
    @Override
    public Notifications getNotifications(Long principalId) {
        Integer totalMessages = messageRepository.getMissedMessages(principalId);

        Notifications notifications = new Notifications();

        notifications.setMessages(totalMessages.longValue());

        return notifications;
    }


}
