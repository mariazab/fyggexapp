package com.example.fyggexapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class RatesFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Currency>> {

    private CurrencyListAdapter adapter;

    private Context context;

    private ProgressBar progressBar;
    private TextView emptyView;

    private static final String URL = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin%2Cethereum%2Ctether%2Cripple%2Cthe-transfer-token%2Cchainlink%2Cbitcoin-cash%2Cpolkadot%2Cbinancecoin%2Cyam&vs_currencies=eur";

    public RatesFragment() {
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
        return inflater.inflate(R.layout.fragment_rates, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();

        RecyclerView recyclerView = getView().findViewById(R.id.rates_recycler_view);
        adapter = new CurrencyListAdapter(context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        progressBar = getView().findViewById(R.id.progress_spinner);
        emptyView = getView().findViewById(R.id.rates_empty_view);

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
    public Loader<List<Currency>> onCreateLoader(int i, Bundle bundle) {
        return new RatesItemLoader(context, URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Currency>> loader, List<Currency> currencyList) {
        //Hide progress bar
        progressBar.setVisibility(View.GONE);

        adapter.setRateList(currencyList);

        //If there are no news items, update the empty view
        //Otherwise, hide the view
        if(adapter.getItemCount() == 0) {
            emptyView.setText(R.string.no_rates_msg);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Currency>> loader) {

    }
}