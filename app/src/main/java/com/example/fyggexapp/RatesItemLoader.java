package com.example.fyggexapp;

import android.content.Context;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class RatesItemLoader extends AsyncTaskLoader<List<Currency>> {

    private String url;

    public RatesItemLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Currency> loadInBackground() {
        if (url == null) {
            return null;
        }

        List<Currency> rateItems = Utils.fetchRatesData(url);
        return rateItems;

    }
}