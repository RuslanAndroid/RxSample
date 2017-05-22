package ru.ruslankhusaenov.supercool.repository;




import retrofit2.http.GET;
import ru.ruslankhusaenov.supercool.models.NewsList;

import rx.Observable;

/**
 * Created by ennur on 6/25/16.
 */
public interface NetworkAPI {

    @GET("v1/articles?source=bbc-news&sortBy=top&apiKey=8f0f234226fc40b392747cf90495510f")
    Observable<NewsList> getNewsList();

}
