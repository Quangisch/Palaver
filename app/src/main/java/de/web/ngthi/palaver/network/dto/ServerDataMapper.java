package de.web.ngthi.palaver.network.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;

import de.web.ngthi.palaver.mvp.model.Message;
import de.web.ngthi.palaver.mvp.model.User;

public class ServerDataMapper {


    private static ObjectMapper mapper = new ObjectMapper();

    public static List<Message> mapToMessages(ServerReply reply) {
        List<Message> messages = new LinkedList<>();
        if(ServerReplyType.isType(ServerReplyType.MESSAGE_GET_OK, reply)
                && reply.hasData()) {

            List<ServerData> data = mapper.convertValue(reply.getData(), new TypeReference<List<ServerData>>() { });
            for(ServerData d : data) {
                User sender = new User(d.getSender());
                User recipient = new User(d.getRecipient());
                String message = d.getServerData();
                DateTime dateTime = DateTime.parse(d.getDateTime());
                messages.add(new Message(sender, recipient, message, dateTime, Message.Status.SENT));
            }
        }

        return messages;
    }

    public static List<User> mapToFriends(ServerReply reply) {
        List<User> friends = new LinkedList<>();
        if(ServerReplyType.isType(ServerReplyType.FRIENDS_GET, reply)
                && reply.hasData()) {

            List<String> data = mapper.convertValue(reply.getData(), new TypeReference<List<String>>() { });
            for(String s : data) {
                friends.add(new User(s));
            }
        }

        return friends;
    }

}
