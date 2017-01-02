package com.glemontree.zhihu.task;

import android.os.AsyncTask;

import com.glemontree.zhihu.adapter.NewsAdapter;
import com.glemontree.zhihu.bean.News;
import com.glemontree.zhihu.http.Http;
import com.glemontree.zhihu.http.JsonHelper;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/12/16.
 */

public class LoadNewsTask extends AsyncTask<Void, Void, List<News>> {
    private NewsAdapter adapter;
    private OnFinishListener onFinishListener;
    public LoadNewsTask(NewsAdapter adapter) {
        super();
        this.adapter = adapter;
    }
    public LoadNewsTask(NewsAdapter adapter, OnFinishListener listener) {
        super();
        this.adapter = adapter;
        this.onFinishListener = listener;
    }

    @Override
    protected List<News> doInBackground(Void... params) {
        List<News> newsList = null;
        try {
            newsList = JsonHelper.parseJsonToNewsList(Http.getLastNewsList());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            return newsList;
        }
    }

    @Override
    protected void onPostExecute(List<News> newsList) {
        adapter.refreshNewsList(newsList);
        if (onFinishListener != null) {
            onFinishListener.afterTaskFinish(); // 更新完毕后调用回调函数
        }
    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }

    public interface OnFinishListener {
        public void afterTaskFinish();
    }
}
