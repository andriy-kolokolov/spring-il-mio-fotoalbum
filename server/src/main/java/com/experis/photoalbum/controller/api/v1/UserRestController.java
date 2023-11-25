package com.experis.photoalbum.controller.api.v1;

import com.experis.photoalbum.dto.MessageDTO;
import com.experis.photoalbum.model.Message;
import com.experis.photoalbum.request.SendMessageRequest;
import com.experis.photoalbum.service.MessageService;
import com.experis.photoalbum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserRestController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}/messages/sent")
    public List<MessageDTO> getUserSentMessages(@PathVariable Long userId) {
        return messageService.getSentMessagesByUserId(userId);
    }

    @GetMapping("/{userId}/messages/received")
    public List<MessageDTO> getUserReceivedMessages(@PathVariable Long userId) {
        return messageService.getReceivedMessagesByUserId(userId);
    }

    // send message endpoint
    @PostMapping("/{senderId}/messages/send")
    public ResponseEntity<MessageDTO> sendMessage(
            @PathVariable Long senderId,
            @RequestBody SendMessageRequest request
    ) {
        Message message = new Message();
        message.setSender(userService.getUserById(senderId));
        message.setReceiver(userService.getUserById(request.getReceiverId()));
        message.setContent(request.getContent());
        message.setSentAt(LocalDateTime.now());

        MessageDTO savedMessage = messageService.saveMessage(message);
        return ResponseEntity.ok(savedMessage);

    }

}
