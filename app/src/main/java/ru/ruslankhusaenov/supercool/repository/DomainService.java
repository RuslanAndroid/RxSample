package ru.ruslankhusaenov.supercool.repository;

import android.content.Context;

import com.fernandocejas.frodo.annotation.RxLogObservable;


import java.util.ArrayList;

import ru.ruslankhusaenov.supercool.models.NewsItem;
import ru.ruslankhusaenov.supercool.models.NewsList;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DomainService {

    private final DiskRepository mDiskRepository;
    private NetworkAPI mAPI;

    public final static String NEWS_LIST_KEY    = "NEWS_LIST";
    public final static String NEWS_ONE_KEY     = "NEWS_ONE";

    public DomainService( NetworkAPI API , Context context) {
        this.mAPI = API;
        mDiskRepository = new DiskRepository(context); // TODO: Inject Singleton
    }


    @RxLogObservable
    public Observable<ArrayList<NewsItem>> getRecentNews() {
        return getMergedNews()
                .filter(new Func1<NewsList, Boolean>() {
                    @Override
                    public Boolean call(NewsList response) {
                        return response != null;
                    }
                })
                .map(new Func1<NewsList, ArrayList<NewsItem>>() {
                    @Override
                    public ArrayList<NewsItem> call(NewsList response) {
                        return response.mNewsItems;
                    }
                });
    }

    @RxLogObservable
    private Observable<NewsList> getMergedNews() {
        return Observable.mergeDelayError(
                mDiskRepository.getRecentNews(NEWS_LIST_KEY).subscribeOn(Schedulers.io()),
                mAPI.getNewsList()
                        .filter(new Func1<NewsList, Boolean>() {
                            @Override
                            public Boolean call(NewsList response) {
                                return response != null;
                            }
                        })
                        .doOnNext(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        mDiskRepository.saveResponse(newsList,NEWS_LIST_KEY);
                    }
                }).subscribeOn(Schedulers.io())
        );
    }


}
