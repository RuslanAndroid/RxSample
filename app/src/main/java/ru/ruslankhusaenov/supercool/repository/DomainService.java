package ru.ruslankhusaenov.supercool.repository;

import android.content.Context;

import com.fernandocejas.frodo.annotation.RxLogObservable;



import java.util.List;



import ru.ruslankhusaenov.supercool.models.NewsItem;
import ru.ruslankhusaenov.supercool.models.NewsList;

import rx.Observable;

import rx.schedulers.Schedulers;

public class DomainService {

    private final DiskRepository mDiskRepository;
    private NetworkAPI mAPI;

    public final static String NEWS_LIST_KEY    = "NEWS_LIST";
    public final static String NEWS_ONE_KEY     = "NEWS_ONE";



    public DomainService( NetworkAPI API , Context context) {
        this.mAPI = API;
        mDiskRepository = new DiskRepository(context);
    }

    @RxLogObservable
    public Observable<List<NewsItem>> getRecentNews() {
        return getMergedNews()
                .filter(response -> response != null)
                .map(response -> response.mNewsItems);
    }

    @RxLogObservable
    private Observable<NewsList> getMergedNews() {
        return Observable.mergeDelayError(
                mDiskRepository.getRecentNews(NEWS_LIST_KEY).subscribeOn(Schedulers.io()),
                mAPI.getNewsList()
                        .filter(response -> response != null)
                        .doOnNext(newsList -> mDiskRepository.saveResponse(newsList,NEWS_LIST_KEY))
                        .subscribeOn(Schedulers.io())
        );
    }

}
