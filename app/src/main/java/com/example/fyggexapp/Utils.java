package com.example.fyggexapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

//Methods for fetching data
public class Utils {

    private static final String TAG = "Utils";

    private Utils() {

    }

    //Fetch the news data from the url
    public static List<NewsItem> fetchNewsData(String requestUrl) {
        //Make new URL object from the String
        URL url = createUrlFromString(requestUrl);

        String response = null;
        try {
            response = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Failed to make the HTTP request, " + e);
        }

        List<NewsItem> newsItems = createNewsListFromJson(response);
        return newsItems;
    }

    //Fetch the rates data from the url
    public static List<Currency> fetchRatesData(String requestUrl) {
        URL url = createUrlFromString(requestUrl);

        String response = null;

        try {
            response = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Failed to make the HTTP request, " + e);
        }

        List<Currency> rateItems = createRatesListFromJson(response);

        return rateItems;
    }

    //Method for making the request
    private static String makeHttpRequest(URL url) throws IOException {
        String response = "";

        //If the URL is null, don't perfom the request and return an empty response
        if(url == null) {
            return response;
        }

        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.connect();

            //Checking if the connection was successful
            //if so, read the input stream and parse the response
            if(connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                response = parseToString(inputStream);
            } else {
                Log.e(TAG, "HTTP Response code: " + connection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e);
        } finally {
            if(connection != null) {
                connection.disconnect();
            }

            if(inputStream != null) {
                inputStream.close();
            }
        }
        return response;
    }

    //Method for parsing the Input Stream into a String
    private static String parseToString(InputStream inputStream) throws IOException {
        StringBuilder response = new StringBuilder();

        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                response.append(line);
                line = bufferedReader.readLine();
            }
        }
        return response.toString();
    }

    //Create a list of NewsItems from JSON String
    private static List<NewsItem> createNewsListFromJson(String json) {
        //If JSON string is null, return null
        if(TextUtils.isEmpty(json)) {
            return null;
        }

        List<NewsItem> newsItems = new ArrayList<>();

        //Parse the JSON
        try {
            //Create JSONArray from JSON string
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i ++) {

                JSONObject currentItem = jsonArray.getJSONObject(i);

                //Get image id from the json and
                //Find the url based on the image id
                String imageId = currentItem.getString("featured_media");
                String imageUrl = getImageUrl(imageId);

                JSONObject titleObject = currentItem.getJSONObject("title");
                String title = titleObject.getString("rendered");
                //Check if title contains &#038 and change it to &
                if(title.contains("&#038;")) {
                    title = title.replace("&#038;", "&");
                }

                //Change the date, so that it matches YYYY-MM-DD
                String date = currentItem.getString("date");
                date = date.substring(0, 10);

                String url = currentItem.getString("link");

                NewsItem newsItem = new NewsItem(imageUrl, title, date, url);

                newsItems.add(newsItem);

            }
        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing the JSON response, " + e.getMessage());
        }
        return newsItems;
    }

    //Method for requesting the image url for the specific news item
    private static String getImageUrl (String imageId) {
        String imageUrl = "";
        String imageInfoUrl = "https://fyggex.com/wp-json/wp/v2/media/" + imageId;

        URL url = createUrlFromString(imageInfoUrl);

        String response = null;
        try {
            response = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Failed to make the HTTP request, " + e);
        }

        if(TextUtils.isEmpty(response)) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(response);
            imageUrl = jsonObject.getString("link");
        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing the JSON response, " + e.getMessage());
        }
        return imageUrl;
    }

    //Method for creating the list with the currencies and rates from json
    private static List<Currency> createRatesListFromJson(String json) {
        //If JSON string is null, return null
        if(TextUtils.isEmpty(json)) {
            return null;
        }

        List<Currency> rateItems = new ArrayList<>();

        //Parse the JSON
        try {
            //Create JSON object from the JSON response
            JSONObject jsonResponse = new JSONObject(json);

            String eur = "eur";

            //Get the JSONObjects with each currency
            JSONObject btc = jsonResponse.getJSONObject(CurrencyNames.BTC_JSON);
            JSONObject eth = jsonResponse.getJSONObject(CurrencyNames.ETH_JSON);
            JSONObject tether = jsonResponse.getJSONObject(CurrencyNames.TETHER_JSON);
            JSONObject xrp = jsonResponse.getJSONObject(CurrencyNames.XRP_JSON);
            JSONObject ttt = jsonResponse.getJSONObject(CurrencyNames.TTT_JSON);
            JSONObject link = jsonResponse.getJSONObject(CurrencyNames.LINK_JSON);
            JSONObject bch = jsonResponse.getJSONObject(CurrencyNames.BCH_JSON);
            JSONObject dot = jsonResponse.getJSONObject(CurrencyNames.DOT_JSON);
            JSONObject bnb = jsonResponse.getJSONObject(CurrencyNames.BNB_JSON);
            JSONObject yam = jsonResponse.getJSONObject(CurrencyNames.YAM_JSON);

            //Create Currency objects for the currencies
            Currency btcC = new Currency(CurrencyNames.BTC, btc.getDouble(eur));
            Currency ethC = new Currency(CurrencyNames.ETH, eth.getDouble(eur));
            Currency tetherC = new Currency(CurrencyNames.TETHER, tether.getDouble(eur));
            Currency xrpC = new Currency(CurrencyNames.XRP, xrp.getDouble(eur));
            Currency tttC = new Currency(CurrencyNames.TTT, ttt.getDouble(eur));
            Currency linkC = new Currency(CurrencyNames.LINK, link.getDouble(eur));
            Currency bchC = new Currency(CurrencyNames.BCH, bch.getDouble(eur));
            Currency dotC = new Currency(CurrencyNames.DOT, dot.getDouble(eur));
            Currency bnbC = new Currency(CurrencyNames.BNB, bnb.getDouble(eur));
            Currency yamC = new Currency(CurrencyNames.YAM, yam.getDouble(eur));

            //Add the Currency objects to the list
            rateItems.add(btcC);
            rateItems.add(ethC);
            rateItems.add(tetherC);
            rateItems.add(xrpC);
            rateItems.add(tttC);
            rateItems.add(linkC);
            rateItems.add(bchC);
            rateItems.add(dotC);
            rateItems.add(bnbC);
            rateItems.add(yamC);

        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing the JSON response, " + e.getMessage());
        }
        return rateItems;
    }

    //Method for creating URL from a String object
    private static URL createUrlFromString(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error creating URL object, " + e);
        }
        return url;
    }
}