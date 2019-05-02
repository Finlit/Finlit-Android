package com.app.finlit.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.app.finlit.R;
import com.app.finlit.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DemoActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    private DemoAdapter adapter;
    private List<DemoModel> list;

    @Override
    protected int getContentId() {
        return R.layout.activity_demo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();

        initAdapter();
    }

    private void initAdapter() {
        int j = 1, k =2, countFirst= 0, countSecond = 0, maxCount = 1;
        for (int i = 0; i<100; i++){
            DemoModel model = new DemoModel();
            if (countFirst<maxCount) {
                ++countFirst;
                model.setValue(j);
            }
            else {
                model.setValue(k);
                ++countSecond;
                if (countSecond == maxCount){
                    ++maxCount;
                    countSecond = 0;
                    countFirst = 0;
                }
            }
            list.add(model);
        }

        adapter  = new DemoAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}
