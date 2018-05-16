package de.web.ngthi.palaver.controller;

import dagger.Module;
import de.web.ngthi.palaver.dto.ServerReply;
import de.web.ngthi.palaver.dto.ServerRequest;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

@Module
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
