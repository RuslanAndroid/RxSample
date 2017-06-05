package ru.ruslankhusaenov.supercool.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.google.gson.Gson;


import javax.inject.Singleton;

import ru.ruslankhusaenov.supercool.models.NewsList;

import rx.Observable;
public class DiskRepository {

    private static final String CLASSNAME = DiskRepository.class.getCanonicalName();

    private Gson mGson = new Gson();
    private final SharedPreferences sharedPreferences;

    @Singleton
    public DiskRepository(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveResponse(NewsList response, String key) {
        sharedPreferences.edit().putString(key, mGson.toJson(response)).apply();
    }

    @RxLogObservable
    public Observable<NewsList> getRecentNews(final String key) {
        return Observable.fromCallable(() -> {
            String sharedPreferencesString = sharedPreferences.getString(key, "");
            NewsList news = null;
            if (!TextUtils.isEmpty(sharedPreferencesString)) {
                news =mGson.fromJson(sharedPreferencesString, NewsList.class);
            }
            return news;
        });
    }

}