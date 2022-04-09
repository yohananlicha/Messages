package com.springboot.service;

import com.springboot.model.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageService {

    private static HashMap<String, List<String>> allMessages = new HashMap<String, List<String>>();

    public List<String> retrieveMessageByRecipient(String recipient) {
       return allMessages.get(recipient);
    }

    public Message sendMessage(Message message){
        List<String> ListMessageByRecipient = new ArrayList<>();
        if(message==null)
            return null;
        if(message.getRecipient()!= null && allMessages.get(message.getRecipient())==null ){
            ListMessageByRecipient.add(message.getText());
            allMessages.put(message.getRecipient(), ListMessageByRecipient );
        }
        else {
            ListMessageByRecipient = allMessages.get(message.getRecipient());
            ListMessageByRecipient.add(message.getText());
        }
        return message;
    }


}
