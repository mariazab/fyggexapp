package com.example.fyggexapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsItem>>, NewsListAdapter.OnItemListener {

    private NewsListAdapter adapter;

    Context context;

    private ProgressBar progressBar;
    private TextView emptyView;

    private static final String URL = "http://fyggex.com/wp-json/wp/v2/posts?per_page=20";

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();

        RecyclerView recyclerView = getView().findViewById(R.id.news_recycler_view);
        adapter = new NewsListAdapter(context, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        progressBar = getView().findViewById(R.id.progress_spinner);
        emptyView = getView().findViewById(R.id.news_empty_view);

        //Check if there is internet connection, if not inform the user
        boolean isInternetConnected = checkInternetConnection();

        if(isInternetConnected) {
            LoaderManager loaderManager = LoaderManager.getInstance(this);
            loaderManager.initLoader(1, null, this);
        } else {
            progressBar.setVisibility(View.GONE);
            emptyView.setText(R.string.no_internet_msg);
        }

    }

    //Method for checking the internet connection
    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    public Loader<List<NewsItem>> onCreateLoader(int i, Bundle bundle) {
        return new NewsItemLoader(context, URL);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> newsItems) {
        //Hide progress bar
        progressBar.setVisibility(View.GONE);

        adapter.setNewsList(newsItems);

        //If there are no news items, update the empty view
        //Otherwise, hide the view
        if(adapter.getItemCount() == 0) {
            emptyView.setText(R.string.no_news_msg);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {

    }

    //On Item Click, open the link in a browser
    @Override
    public void onItemClick(int position) {
        //Get the news Item at the position
        NewsItem item = adapter.getNewsItemByPosition(position);

        //Convert the String into a URI object
        Uri uri = Uri.parse(item.getUrl());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}