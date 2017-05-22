package ru.ruslankhusaenov.supercool.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruslan on 19.05.2017.
 */

public class NewsList {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("source")
    @Expose
    public String source;
    @SerializedName("sortBy")
    @Expose
    public String sortBy;
    @SerializedName("articles")
    @Expose
    public List<NewsItem> mNewsItems = null;

}
