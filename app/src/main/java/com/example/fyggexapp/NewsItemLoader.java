package com.example.fyggexapp;

import android.content.Context;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class NewsItemLoader extends AsyncTaskLoader<List<NewsItem>> {

    private String url;

    public NewsItemLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<NewsItem> loadInBackground() {

        if(url == null) {
            return null;
        }

        List<NewsItem> newsItems = Utils.fetchNewsData(url);
        return newsItems;
    }
}