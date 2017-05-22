package ru.ruslankhusaenov.supercool.repository;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class NetworkRepository {

    private Context mContext;
    private static final String CLASSNAME = NetworkRepository.class.getCanonicalName();
    private static final String ENDPOINT = "https://newsapi.org/";

    public NetworkRepository(Context context){
        this.mContext = context;
    }

    @Provides
    @Singleton
    Retrofit provideCall() {
        return new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public NetworkAPI providesNetworkService(
            Retrofit retrofit) {
        return retrofit.create(NetworkAPI.class);
    }
    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public DomainService providesService(
            NetworkAPI networkService) {
        return new DomainService(networkService , mContext);
    }
}
