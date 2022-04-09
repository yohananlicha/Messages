package com.springboot.model;

public class Message {

         String sender;
         String recipient;
         String text;

         public Message(String sender, String recipient, String text){
            this.sender=sender;
            this.recipient=recipient;
            this.text=text;
        }

    public Message(Message message){
        this.sender=message.sender;
        this.recipient=message.recipient;
        this.text=message.text;
    }

        public String getSender() {
             return sender;
        }
          public String getRecipient(){
            return recipient;
        }

        public String getText(){
            return text;
        }
}
