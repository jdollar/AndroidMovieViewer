package org.dollarhide.androidmovieviewer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.dollarhide.androidmovieviewer.application.MovieViewApplication;
import org.dollarhide.androidmovieviewer.movieviewer.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultAdapter extends BaseAdapter {

    private List<String> keyList;
    private Map<String, String> results;

    public ResultAdapter(Map<String, String> results) {
        this.results = results;
        keyList = new ArrayList<>();
        for (Map.Entry<String, String> entry : results.entrySet()) {
            keyList.add(entry.getKey());
        }
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public String getItem(int position) {
        String key = keyList.get(position);
        return results.get(key);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public String getItemKey(int position) {
        return keyList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) MovieViewApplication.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.search_result_item, null);
        }

        String text = getItem(position);
        TextView resultText = (TextView) row.findViewById(R.id.result_text);

        resultText.setText(text);

        return row;
    }
}
