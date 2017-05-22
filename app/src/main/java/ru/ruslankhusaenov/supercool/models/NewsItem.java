package ru.ruslankhusaenov.supercool.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ruslan on 19.05.2017.
 */

public class NewsItem implements Serializable{

    @SerializedName("author")
    @Expose
    public String author;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("urlToImage")
    @Expose
    public String urlToImage;
    @SerializedName("publishedAt")
    @Expose
    public String publishedAt;

}