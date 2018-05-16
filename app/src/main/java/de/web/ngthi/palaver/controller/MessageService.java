package de.web.ngthi.palaver.controller;

import dagger.Module;
import de.web.ngthi.palaver.dto.ServerReply;
import de.web.ngthi.palaver.dto.ServerRequest;
import retrofit2.http.Body;
import retrofit2.http.POST;

@Module
public interface MessageService {

    @POST("message/send")
    ServerReply sendMessage(@Body ServerRequest request);

    @POST("message/get")
    ServerReply getMessages(@Body ServerRequest request);

    @POST("message/getoffset")
    ServerReply getMessagesOffset(@Body ServerRequest request);

}
