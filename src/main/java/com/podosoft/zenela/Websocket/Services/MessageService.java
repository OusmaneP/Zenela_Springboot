package com.podosoft.zenela.Websocket.Services;

import com.podosoft.zenela.Models.User;
import com.podosoft.zenela.Websocket.models.Message;
import com.podosoft.zenela.Websocket.models.MessageDto;

import java.util.Collection;

public interface MessageService {
    void saveMessage(MessageDto messageDto, String to);

    Collection<Message> getMessages(Long principalId, Long user2);

    int setMessageSeen(String principalId, String fromLogin);

    int getMissedMessages(Long principalId, Long sender);

    Collection<User> getSomeMessages(Long principalId);
}
