package com.example.newsapp;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils(){}

    public static List<News> extractNews(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch(IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        List<News> newsList = extractFeatureFromJson(jsonResponse);
        return  newsList;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if(url == null) {
            return jsonResponse;
        }

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.connect();

            if(connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retriving the news json result.", e);
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
            if(inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    private static List<News> extractFeatureFromJson(String newsJson) {
        if(TextUtils.isEmpty(newsJson)) {
            return null;
        }

        List<News> newsList = new ArrayList<News>();
        try {
            JSONObject baseJsonResponse = new JSONObject(newsJson);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray results= response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String sectionName = result.getString("sectionName");
                String authorName = "";
                if (result.has("authorName")) {
                    authorName = result.getString("authorName");
                }
                String datePublishedString = "";
                if (result.has("webPublicationDate")) {
                    datePublishedString = result.getString("webPublicationDate");
                }
                String title = result.getString("webTitle");
                String url = result.getString("webUrl");

                News news = null;
                if (authorName.length() == 0) {
                    if (datePublishedString.length() == 0) {
                        news = new News(title, sectionName, url);
                    } else {
                        try {
                            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(datePublishedString);
                            news = new News(title, sectionName, date, url);
                        } catch (ParseException ex) {
                            Log.e(LOG_TAG, "Error parsing date");
                        }
                    }
                } else {
                    if (datePublishedString.length() == 0) {
                        news = new News(title, sectionName, authorName, url);
                    } else {
                        try {
                            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(datePublishedString);
                            news = new News(title, sectionName, authorName, date, url);
                        } catch (ParseException ex) {
                            Log.e(LOG_TAG, "Error parsing date");
                        }
                    }
                }
                newsList.add(news);
            }
            return newsList;
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the news JSON results", e);
        }
        return newsList;
    }
}
