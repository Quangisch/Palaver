package de.web.ngthi.palaver.controller;

import dagger.Module;
import de.web.ngthi.palaver.dto.ServerReply;
import de.web.ngthi.palaver.dto.ServerRequest;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

@Module
public interface MessageService {

    @POST("message/send")
    Single<ServerReply> sendMessage(@Body ServerRequest request);

    @POST("message/get")
    Single<ServerReply> getMessages(@Body ServerRequest request);

    @POST("message/getoffset")
    Single<ServerReply> getMessagesOffset(@Body ServerRequest request);

}
