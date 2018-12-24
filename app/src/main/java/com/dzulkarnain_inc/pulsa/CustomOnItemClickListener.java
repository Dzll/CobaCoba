package com.dzulkarnain_inc.pulsa;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeoutException;

/**
 * Created by Dzulkarnain Inc on 22/06/2018.
 */

public class CustomOnItemClickListener implements View.OnClickListener {
    private int position;
    private OnItemClickCallback onItemClickCallback;
    public CustomOnItemClickListener(int position, OnItemClickCallback onItemClickCallback) {
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onClick(View view) {
        onItemClickCallback.onItemClicked(view, position);
    }
    public interface OnItemClickCallback {
        void onItemClicked(View view, int position);
    }
}
