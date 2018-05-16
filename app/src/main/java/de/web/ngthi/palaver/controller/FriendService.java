package de.web.ngthi.palaver.controller;

import de.web.ngthi.palaver.dto.ServerReply;
import de.web.ngthi.palaver.dto.ServerRequest;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FriendService {

    @POST("friends/add")
    ServerReply addFriend(@Body ServerRequest request);

    @POST("friends/remove")
    ServerReply removeFriend(@Body ServerRequest request);

    @POST("friends/get")
    ServerReply getFriends(@Body ServerRequest request);


}
