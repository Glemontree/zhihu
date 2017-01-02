package com.glemontree.zhihu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.glemontree.zhihu.R;
import com.glemontree.zhihu.bean.News;
import com.glemontree.zhihu.db.DailyNewsDB;
import com.glemontree.zhihu.task.LoadNewsDetailTask;
import com.glemontree.zhihu.utility.Utility;

/**
 * Created by Administrator on 2016/12/17.
 */

public class NewsDailyActivity extends AppCompatActivity {
    private WebView webView;
    private boolean isFavourite = false;
    private News news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        webView = (WebView) findViewById(R.id.webview);
        setWebView();
        news = (News)getIntent().getSerializableExtra("news");
        new LoadNewsDetailTask(webView).execute(news.getId());
        isFavourite = DailyNewsDB.getInstance(this).isFavourite(news);
    }

    private void setWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
    }

    public static void startActivity(Context context, News news) {
        if (Utility.checkNetworkConnection(context)) {
            Intent i = new Intent(context, NewsDailyActivity.class);
            i.putExtra("news", news);
            context.startActivity(i);
        } else {
            Utility.noNetworkAlert(context);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (isFavourite) {
            menu.findItem(R.id.action_favourite).setIcon(R.drawable.fav_active);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_favourite:
                if (!isFavourite) {
                    DailyNewsDB.getInstance(this).saveFavourite(news);
                    item.setIcon(R.drawable.fav_active);
                    isFavourite = true;
                } else {
                    DailyNewsDB.getInstance(this).deleteFavourite(news);
                    item.setIcon(R.drawable.fav_normal);
                    isFavourite = false;
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
