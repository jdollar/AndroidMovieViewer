package org.dollarhide.androidmovieviewer;

import android.view.View;
import android.widget.AdapterView;
import org.dollarhide.androidmovieviewer.adapter.ResultAdapter;
import org.dollarhide.androidmovieviewer.application.MovieViewApplication;

public class ResultView extends AdapterView<ResultAdapter> {

    private ResultAdapter adapter;

    public ResultView() {
        super(MovieViewApplication.getContext());
    }

    @Override
    public ResultAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void setAdapter(ResultAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setSelection(int position) {

    }
}
