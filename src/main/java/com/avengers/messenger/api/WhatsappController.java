package com.avengers.messenger.api;

import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("whatsapp")
public class WhatsappController {

    WhatsappService whatsappService = new WhatsappService();

    @PostMapping("/add-user")
    public String createUser(@RequestParam("name") String name, @RequestParam("mobile") String mobile) throws Exception {

        String response = whatsappService.createUser(name, mobile);
        if(response.equals("User already exists")) throw new Exception("User already exists");
        return response;
//        return whatsappService.createUser(name, mobile);
    }

    @PostMapping("/add-group")
    public Group createGroup(@RequestBody List<User> users){
        return whatsappService.createGroup(users);
    }

    @PostMapping("/add-message")
    public int createMessage(@RequestParam("content") String content){

        return whatsappService.createMessage(content);
    }

    @PutMapping("/send-message")
    public int sendMessage(@RequestBody Message message, @RequestBody User sender, @RequestBody Group group) throws Exception{

        int response = whatsappService.sendMessage(message, sender, group);
        if(response == -1) throw new Exception("Group does not exist");
        if(response == -2) throw new Exception("You are not allowed to send message");
        return response;
//        return whatsappService.sendMessage(message, sender, group);
    }
    @PutMapping("/change-admin")
    public String changeAdmin(@RequestBody User approver, @RequestBody User user, @RequestBody Group group) throws Exception{

        String response = whatsappService.changeAdmin(approver, user, group);
        if(response.equals("Group does not exist")) throw new Exception("Group does not exist");
        if(response.equals("Approver does not have rights")) throw new Exception("Approver does not have rights");
        if(response.equals("User is not a participant")) throw new Exception("User is not a participant");

        return response;
    }
}
