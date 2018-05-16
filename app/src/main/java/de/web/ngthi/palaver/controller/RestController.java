package de.web.ngthi.palaver.controller;


import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class RestController {

    @Inject
    public RestController() {

    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://palaver.se.paluno.uni-due.de/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public UserService provideUserService(Retrofit retrofit) {
        return retrofit.create(UserService.class);
    }


    @Provides
    @Singleton
    public MessageService provideMessageService(Retrofit retrofit) {
        return retrofit.create(MessageService.class);
    }

    @Provides
    @Singleton
    public FriendsService provideFriendService(Retrofit retrofit) {
        return retrofit.create(FriendsService.class);
    }

}
