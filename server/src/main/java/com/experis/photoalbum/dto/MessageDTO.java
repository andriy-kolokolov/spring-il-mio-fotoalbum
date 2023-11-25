package com.experis.photoalbum.dto;

import com.experis.photoalbum.model.Message;
import com.experis.photoalbum.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MessageDTO {
    private Long id;
    private String content;
    private String senderUsername;
    private String receiverUsername;
    private LocalDateTime sentAt;

    private static UserService userService;

    @Autowired
    public MessageDTO(UserService userService) {
        MessageDTO.userService = userService;
    }

    public static MessageDTO fromMessage(Message message) {
        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setContent(message.getContent());
        dto.setSenderUsername(userService.getUserById(message.getSender().getId()).getUsername());
        dto.setReceiverUsername(userService.getUserById(message.getReceiver().getId()).getUsername());
        dto.setSentAt(message.getSentAt());


        return dto;
    }
}
