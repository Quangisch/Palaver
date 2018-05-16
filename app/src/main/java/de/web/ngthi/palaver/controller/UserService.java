package de.web.ngthi.palaver.controller;

import de.web.ngthi.palaver.dto.ServerReply;
import de.web.ngthi.palaver.dto.ServerRequest;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    @POST("user/register")
    Single<ServerReply> register(@Body ServerRequest request);

    @POST("user/validate")
    Single<ServerReply> validate(@Body ServerRequest request);

    @POST("user/password")
    Single<ServerReply> changePassword(@Body ServerRequest request);

    @POST("user/pushtoken")
    Single<ServerReply> refreshToken(@Body ServerRequest request);

}
