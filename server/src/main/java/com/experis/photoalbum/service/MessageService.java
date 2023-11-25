package com.experis.photoalbum.service;

import com.experis.photoalbum.dto.MessageDTO;
import com.experis.photoalbum.model.Message;
import com.experis.photoalbum.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

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
}
