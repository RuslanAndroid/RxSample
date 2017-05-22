package ru.ruslankhusaenov.supercool.fragments.news;


import java.util.ArrayList;
import java.util.List;

import ru.ruslankhusaenov.supercool.models.NewsItem;


/**
 * Created by ennur on 6/25/16.
 */
public interface NewsView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void onLoad(List<NewsItem> cityListResponse);

    void onLoadMore(List<NewsItem> cityListResponse);

}
