package ru.ruslankhusaenov.supercool.fragments.news;


import java.util.ArrayList;

import ru.ruslankhusaenov.supercool.models.NewsItem;


/**
 * Created by ennur on 6/25/16.
 */
public interface NewsView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void onLoad(ArrayList<NewsItem> cityListResponse);

    void onLoadMore(ArrayList<NewsItem> cityListResponse);

}
