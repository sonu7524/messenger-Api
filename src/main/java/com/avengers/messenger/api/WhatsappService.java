package com.avengers.messenger.api;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WhatsappService {

    WhatsappRepository whatsappRepository = new WhatsappRepository();
    public String createUser(String name, String mobile){
        return whatsappRepository.createUser(name, mobile);
    }

    public Group createGroup(List<User> users){
        return whatsappRepository.createGroup(users);
    }

    public int createMessage(String content){
        return whatsappRepository.createMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group){
        return whatsappRepository.sendMessage(message, sender, group);
    }

    public String changeAdmin(User approver, User user, Group group){
        return whatsappRepository.changeAdmin(approver, user, group);
    }
}