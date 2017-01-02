package com.glemontree.zhihu.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.glemontree.zhihu.R;
import com.glemontree.zhihu.adapter.NewsAdapter;
import com.glemontree.zhihu.task.LoadNewsTask;
import com.glemontree.zhihu.utility.Utility;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        AdapterView.OnItemClickListener {
    private SwipeRefreshLayout refreshLayout;
    private ListView listView;
    private NewsAdapter adapter;
    private boolean isConnected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isConnected = Utility.checkNetworkConnection(this);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        listView = (ListView) findViewById(R.id.lv);
        adapter = new NewsAdapter(this, R.layout.listview_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        if (isConnected) new LoadNewsTask(adapter).execute();
        else Utility.noNetworkAlert(this);
    }

    @Override
    public void onRefresh() {
        if(isConnected) {
            new LoadNewsTask(adapter, new LoadNewsTask.OnFinishListener() {
                @Override
                public void afterTaskFinish() {
                    refreshLayout.setRefreshing(false);
                }
            }).execute();
        } else {
            Utility.noNetworkAlert(MainActivity.this);
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this, NewsDailyActivity.class);
        intent.putExtra("news", adapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_favourite:
                Intent i = new Intent(this, FavouriteActivity.class);
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
