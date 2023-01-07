package com.podosoft.zenela.Websocket.controllers;

import com.podosoft.zenela.Dto.MissedMessage;
import com.podosoft.zenela.Websocket.Services.MessageService;
import com.podosoft.zenela.Websocket.models.Message;
import com.podosoft.zenela.Websocket.models.MessageDto;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@CrossOrigin
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    public MessageController(SimpMessagingTemplate simpMessagingTemplate, MessageService messageService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService = messageService;
    }

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageDto messageDto){
        messageService.saveMessage(messageDto, to);
        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, messageDto);
    }

    @GetMapping("/get_messages/{principalId}/{user2}")
    public Collection<Message> getMessages(@PathVariable("user2") String user2, @PathVariable("principalId") String principalId){

        Collection<Message>  messages = messageService.getMessages(Long.valueOf(principalId), Long.valueOf(user2));

        return messages;
    }

    @GetMapping("/set_message_seen/{principalId}/{fromLogin}")
    public int SetMessageSeen(@PathVariable("principalId") String principalId, @PathVariable("fromLogin") String fromLogin){
        return messageService.setMessageSeen(principalId, fromLogin);
    }

//    @GetMapping("/get_missed_messages/{principalId}/{fromLogin}")
//    public MissedMessage getMissedMessages(@PathVariable("principalId") String principalId, @PathVariable("fromLogin") String fromLogin){
//        int missedCount = messageService.getMissedMessages(Long.valueOf(principalId), Long.valueOf(fromLogin));
//        return new MissedMessage(Long.valueOf(fromLogin), missedCount);
//    }

}
