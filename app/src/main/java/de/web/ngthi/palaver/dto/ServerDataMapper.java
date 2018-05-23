package de.web.ngthi.palaver.dto;

import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;

import de.web.ngthi.palaver.model.Message;
import de.web.ngthi.palaver.model.User;

public class ServerDataMapper {

    public static List<Message> mapToMessages(ServerReply reply) {
        List<Message> messages = new LinkedList<>();
        if(ServerReplyType.isType(ServerReplyType.MESSAGE_GET_OK, reply)
                && reply.hasDataArray()) {

            ServerData[] data = (ServerData[]) reply.getData();

            for(ServerData d: data) {
                User sender = new User(d.getSender());
                User recipient = new User(d.getRecipient());
                String message = d.getServerData();
                DateTime dateTime = d.getDateTime();
                messages.add(new Message(sender, recipient, message, dateTime));
            }
        }

        return messages;
    }

    public static List<User> mapToFriends(ServerReply reply) {
        List<User> friends = new LinkedList<>();
        if(ServerReplyType.isType(ServerReplyType.FRIENDS_GET, reply)
                && reply.hasStringArray()) {

            String[] data = (String[]) reply.getData();
            for(String s : data) {
                friends.add(new User(s));
            }
        }
        return friends;
    }




}
