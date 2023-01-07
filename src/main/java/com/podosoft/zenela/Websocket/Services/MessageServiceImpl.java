package com.podosoft.zenela.Websocket.Services;

import com.podosoft.zenela.Models.User;
import com.podosoft.zenela.Repositories.UserRepository;
import com.podosoft.zenela.Websocket.Repository.MessageRepository;
import com.podosoft.zenela.Websocket.models.Message;
import com.podosoft.zenela.Websocket.models.MessageDto;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageServiceImpl implements MessageService{
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveMessage(MessageDto messageDto, String to) {
        Message message1 = new Message(
                        Long.valueOf(messageDto.getFromLogin()),
                        Long.valueOf(to),
                        Long.valueOf(messageDto.getFromLogin()),
                        messageDto.getMessage(),
                true,
                        new Date()
                );

        Message message2 = new Message(
                Long.valueOf(messageDto.getFromLogin()),
                Long.valueOf(to),
                Long.valueOf(to),
                messageDto.getMessage(),
                false,
                new Date()
        );

        messageRepository.saveAll(Arrays.asList(message1, message2));
    }

    @Override
    public Collection<Message> getMessages(Long principalId, Long user2) {
        return messageRepository.getMyMessages(principalId, user2);
    }

    @Override
    public int setMessageSeen(String principalId, String fromLogin) {
        return messageRepository.setMessageSeen(Long.valueOf(fromLogin), Long.valueOf(principalId));
    }

    @Override
    public int getMissedMessages(Long principalId, Long sender) {
        Collection<Message> messages = messageRepository.findAllByReceiverAndSenderAndOwnerAndStatus(principalId,sender, principalId, false);
        return messages.size();
    }

    @Override
    public Collection<User> getSomeMessages(Long principalId){
        Collection<Message> messages = messageRepository.findAllByOwnerOrderByIdDesc(principalId);
        ArrayList<Long> friendsIds = new ArrayList<>();

        for (Message message: messages) {
            if (!Objects.equals(message.getSender(), principalId)){
                if (!friendsIds.contains(message.getSender()))
                    friendsIds.add(message.getSender());
            }
            else {
                if (!friendsIds.contains(message.getReceiver()))
                    friendsIds.add(message.getReceiver());
            }
        }

        Collection<User> customUsers = new ArrayList<>();
        for (Long id: friendsIds) {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()){
                customUsers.add(user.get());
            }
        }

        return customUsers;

    }
}
