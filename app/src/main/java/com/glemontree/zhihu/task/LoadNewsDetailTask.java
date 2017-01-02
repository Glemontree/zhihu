package com.glemontree.zhihu.task;

import android.os.AsyncTask;
import android.webkit.WebView;

import com.glemontree.zhihu.bean.NewsDetail;
import com.glemontree.zhihu.http.Http;
import com.glemontree.zhihu.http.JsonHelper;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Administrator on 2016/12/17.
 */

public class LoadNewsDetailTask extends AsyncTask<Integer, Void, NewsDetail> {
    private WebView webView;
    public LoadNewsDetailTask(WebView webView) {
        this.webView = webView;
    }

    @Override
    protected NewsDetail doInBackground(Integer... params) {
        NewsDetail newsDetail = null;
        try {
            newsDetail = JsonHelper.parseJsonToDetail(Http.getNewsDetail(params[0]));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            return newsDetail;
        }
    }

    @Override
    protected void onPostExecute(NewsDetail newsDetail) {
        String headImage;
        if (newsDetail.getImage() == null || newsDetail.getImage() == "") {
            headImage = "file:///android_asset/news_detail_header_image.jpg";
        } else {
            headImage = newsDetail.getImage();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"img-wrap\">")
                .append("<h1 class=\"headline-title\">")
                .append(newsDetail.getTitle()).append("</h1>")
                .append("<span class=\"img-source\">")
                .append(newsDetail.getImage_source()).append("</span>")
                .append("<img src=\"").append(headImage)
                .append("\" alt=\"\">")
                .append("<div class=\"img-mask\"></div>");
        String mNewsContent = "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_content_style.css\"/>"
                + "<link rel=\"stylesheet\" type=\"text/css\" href=\"news_header_style.css\"/>"
                + newsDetail.getBody().replace("<div class=\"img-place-holder\">", sb.toString());
        webView.loadDataWithBaseURL("file:///android_asset/", mNewsContent, "text/html", "UTF-8", null);
    }
}
