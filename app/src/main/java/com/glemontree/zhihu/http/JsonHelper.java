package com.glemontree.zhihu.http;

import android.util.Log;

import com.glemontree.zhihu.bean.News;
import com.glemontree.zhihu.bean.NewsDetail;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/16.
 */

public class JsonHelper {
    public static List<News> parseJsonToNewsList(String json) throws JSONException {
        JSONObject newsContent = new JSONObject(json);
        JSONArray newsArray = newsContent.getJSONArray("stories");
        List<News> newsList = new ArrayList<>();
        for (int i = 0; i < newsArray.length(); i++) {
            Log.d("MainActivity", "hello");
            JSONObject newsInJson = newsArray.getJSONObject(i);
            int id = newsInJson.optInt("id");
            String title = newsInJson.optString("title");
            String image = "";
            if (newsInJson.has("images")) {
                Log.d("MainActivity", "has image");
                image = (String) newsInJson.getJSONArray("images").get(0);
            }
            News news = new News(id, title, image);
            newsList.add(news);
        }
        return newsList;
    }

    public static NewsDetail parseJsonToDetail(String json) throws JSONException {
        Gson gson = new Gson();
        return gson.fromJson(json, NewsDetail.class);
    }
}
