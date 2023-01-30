package com.avengers.messenger.api;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
@Repository
public class WhatsappRepository {

    private HashMap<Message, User> senderMap;
    private HashSet<String> userMobile;



    private HashMap<String, String> user = new HashMap<>(); //cr
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, User> adminMap;
    private int customGroupCount;
    private int messageId;
    private HashMap<Group, List<Message>> groupMessageMap;


    private HashMap<Integer, String> contentMap = new HashMap<>(); //cr


    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public String createUser(String name, String mobile){
        if(user.containsKey(mobile))
            return "User already exists";

        user.put(mobile, name);
        return "SUCCESS";
    }
    //    ??? A user can belong to exactly one group and has a unique name.
    public Group createGroup(List<User> users){
        if(users.size() < 2) return null;

        //personal chat
        if(users.size() == 2){
            Group group = new Group(users.get(1).getName(), users.size()); //creating group
            //update database of group
            groupUserMap.put(group, users);
            //update database of admin
            adminMap.put(group, users.get(0));
            return group;
        }

        customGroupCount++;
        Group group = new Group("Group "+customGroupCount, users.size());
        groupUserMap.put(group, users);
        adminMap.put(group, users.get(0));//the first user is the admin
        return group;
    }

    public int createMessage(String content){
        messageId++;
        contentMap.put(messageId, content);//database ??
        Message message = new Message(messageId, content);

        return messageId;
    }

    public int sendMessage(Message message, User sender, Group group){

        if(!groupUserMap.containsKey(group))
            return -1;
//        if(!groupMessageMap.containsKey(group))
//            return -1;//Group does not exist

        List<User> list = groupUserMap.get(group);
        if(!list.contains(sender))//if the sender is not a member of the group
            return -2;//You are not allowed to send message

//        List<Message> msg = groupMessageMap.get(group);
        List<Message> msg = new ArrayList<>();
        if(groupMessageMap.containsKey(group)){
            msg = groupMessageMap.get(group);
            msg.add(message);
            groupMessageMap.put(group, msg);
            return msg.size();
        }
        //not present --> chat not started
        msg.add(message);
        groupMessageMap.put(group, msg);
        return msg.size();//final number of messages in that group
    }

    public String changeAdmin(User approver, User user, Group group){
        if(!adminMap.containsKey(group))//group does not exist
            return "Group does not exist";

        //if(adminMap.get(group) != approver)//approver is not the current admin of the group
        if(!adminMap.get(group).equals(approver))
            return "Approver does not have rights";

        List<User> list = groupUserMap.get(group);
        if(!list.contains(user))//user is not a part of the group
            return "User is not a participant";

        adminMap.put(group, user);//Change the admin of the group to "user"
        return "SUCCESS";
    }
}