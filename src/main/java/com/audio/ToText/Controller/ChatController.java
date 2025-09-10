package com.audio.ToText.Controller;

import com.audio.ToText.Model.ChatMessage;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class ChatController {
    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
   public ChatMessage sendMessage(ChatMessage message, Principal principal) {
        if (message.getSender() == null || message.getSender().isEmpty()) {
            message.setSender(principal.getName()); // logged-in username
        }
        return message;
    }

    @GetMapping("/chat")
    public String chat(){
        return "chat";
    }
     @GetMapping("/login")
    public String login(){
        return "login";
    }

}
