package com.experis.photoalbum.service;

import com.experis.photoalbum.dto.MessageDTO;
import com.experis.photoalbum.model.Message;
import com.experis.photoalbum.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;

    public List<MessageDTO> getSentMessagesByUserId(Long userId) {
        List<Message> sentMessages = messageRepository.findBySenderId(userId);
        return sentMessages.stream()
                .map(MessageDTO::fromMessage)
                .collect(Collectors.toList());
    }

    public List<MessageDTO> getReceivedMessagesByUserId(Long userId) {
        List<Message> receivedMessages = messageRepository.findByReceiverId(userId);
        return receivedMessages.stream()
                .map(MessageDTO::fromMessage)
                .collect(Collectors.toList());
    }

    // sen message
    public MessageDTO saveMessage(Message message) {
        message.setSender(userService.getUserById(message.getSender().getId()));
        message.setReceiver(userService.getUserById(message.getReceiver().getId()));
        message.setContent(message.getContent());
        message.setSentAt(LocalDateTime.now());
        return MessageDTO.fromMessage(messageRepository.save(message)); // save the message and return the DTO
    }
}
