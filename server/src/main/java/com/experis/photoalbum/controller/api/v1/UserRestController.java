package com.experis.photoalbum.controller.api.v1;

import com.experis.photoalbum.dto.MessageDTO;
import com.experis.photoalbum.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserRestController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/{userId}/messages/sent")
    public List<MessageDTO> getUserSentMessages(@PathVariable Long userId) {
        return messageService.getSentMessagesByUserId(userId);
    }

    @GetMapping("/{userId}/messages/received")
    public List<MessageDTO> getUserReceivedMessages(@PathVariable Long userId) {
        return messageService.getReceivedMessagesByUserId(userId);
    }

}
