package com.glemontree.zhihu.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.glemontree.zhihu.R;
import com.glemontree.zhihu.adapter.NewsAdapter;
import com.glemontree.zhihu.bean.News;
import com.glemontree.zhihu.db.DailyNewsDB;

import java.util.List;

/**
 * Created by Administrator on 2017/1/2.
 */

public class FavouriteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private NewsAdapter adapter;
    private List<News> favouriteList;
    private ListView lvFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite);
        lvFavourite = (ListView) findViewById(R.id.lv_fav);
        favouriteList = DailyNewsDB.getInstance(this).loadFavourite();
        adapter = new NewsAdapter(this, R.layout.listview_item, favouriteList);
        lvFavourite.setAdapter(adapter);
        lvFavourite.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsDailyActivity.startActivity(this, favouriteList.get(position));
    }
}
