package ru.ruslankhusaenov.supercool.fragments.news;



import android.util.Log;



import java.util.ArrayList;


import ru.ruslankhusaenov.supercool.models.NewsItem;
import ru.ruslankhusaenov.supercool.repository.DomainService;
import ru.ruslankhusaenov.supercool.repository.NetworkError;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;


public class NewsPresenter {
    public static String TAG = "NewsPresenter";
    private final DomainService service;
    private final NewsView view;
    private CompositeSubscription subscriptions;


    public NewsPresenter(DomainService service, NewsView view) {
        this.service        = service;
        this.view           = view;
        this.subscriptions  = new CompositeSubscription();
        getNewsList();
    }

    private void getNewsList() {
        view.showWait();

        Subscription subscription = service
                .getRecentNews()
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(
                        new Action1<ArrayList<NewsItem>>() {
                               @Override
                               public void call(ArrayList<NewsItem> list) {
                                   view.removeWait();
                                   view.onLoad(list);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                view.removeWait();
                                view.onFailure(new NetworkError(throwable).getAppErrorMessage());
                            }
                        });

        subscriptions.add(subscription);

    }

    public void onStop() {
        subscriptions.unsubscribe();
    }



}
