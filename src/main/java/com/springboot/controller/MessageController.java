package com.springboot.controller;
import com.springboot.model.Message;
import com.springboot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;


@RestController

public class MessageController {

    @Autowired
    private MessageService messageService;

    private static ArrayList<Message> allMessages = new ArrayList<Message>();


    @PostMapping("/SendMessage")
    public ResponseEntity<Void> sendMessage(@RequestBody Message newMessage) {
        Message message= messageService.sendMessage(newMessage);
        if (message==null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{id}").buildAndExpand(message.getRecipient()).toUri();;
        return  ResponseEntity.created(location).build();

    }

    @GetMapping ("/ReceiveMessages")
    public List<String> recieveMessages(@RequestParam("recipient") String recipient){
           return messageService.retrieveMessageByRecipient(recipient);
    }
}

