package ru.ruslankhusaenov.supercool.fragments.news;




import ru.ruslankhusaenov.supercool.R;
import ru.ruslankhusaenov.supercool.models.NewsItem;
import ru.ruslankhusaenov.supercool.repository.DomainService;
import ru.ruslankhusaenov.supercool.util.NetworkError;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;


public class NewsPresenter {
    public static String TAG = "NewsPresenter";
    private  String no_date = "No date";
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
                .flatMap(Observable::from)
                .map(this::setDate)
                .toList()
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(
                        list -> {
                            view.removeWait();
                            view.onLoad(list);
                        },
                        throwable -> {
                            view.removeWait();
                            view.onFailure(new NetworkError(throwable).getAppErrorMessage());
                        });

        subscriptions.add(subscription);

    }

    public void onStop() {
        subscriptions.unsubscribe();
    }

    private NewsItem setDate(NewsItem item){
        if(item.publishedAt != null){
            item.publishedAt = item.publishedAt.substring(0,10);
        }else{
            item.publishedAt = no_date;
        }
        return item;
    }

}
