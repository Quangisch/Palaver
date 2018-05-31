package de.web.ngthi.palaver.network.controller;

import dagger.Module;
import de.web.ngthi.palaver.network.dto.ServerReply;
import de.web.ngthi.palaver.network.dto.ServerRequest;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

@Module
public interface FriendsService {

    @POST("friends/add")
    Single<ServerReply> addFriend(@Body ServerRequest request);

    @POST("friends/remove")
    Single<ServerReply> removeFriend(@Body ServerRequest request);

    @POST("friends/get")
    Single<ServerReply> getFriends(@Body ServerRequest request);


}
