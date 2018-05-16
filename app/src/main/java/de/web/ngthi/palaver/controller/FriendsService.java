package de.web.ngthi.palaver.controller;

import dagger.Module;
import de.web.ngthi.palaver.dto.ServerReply;
import de.web.ngthi.palaver.dto.ServerRequest;
import retrofit2.http.Body;
import retrofit2.http.POST;

@Module
public interface FriendsService {

    @POST("friends/add")
    ServerReply addFriend(@Body ServerRequest request);

    @POST("friends/remove")
    ServerReply removeFriend(@Body ServerRequest request);

    @POST("friends/get")
    ServerReply getFriends(@Body ServerRequest request);


}
