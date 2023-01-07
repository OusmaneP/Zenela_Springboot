package com.podosoft.zenela.Services;

import com.podosoft.zenela.Dto.Notifications;
import com.podosoft.zenela.Dto.Responses.SearchResponse;
import com.podosoft.zenela.Models.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MainService {
    User findPrincipal(String email);

    String uploadCoverProfile(String fileName, MultipartFile multipartFile, String comment, Long id, String cover) throws IOException;

    Collection<User> getRandomUsers(Long principalId, List<Long> friendRequestsIds);

    Collection<User> getFriendRequests(Long principalId);

    // Invited Friends
    Collection<User> getInvitedFriends(Long principalId);

    Collection<User> getFriendShips(Long principalId);

    Collection<User> getMyFriends(Long principalId);

    Optional<User> findUserById(Long userId);

    SearchResponse searchPeople(String query);

    Notifications getNotifications(Long principalId);
}
