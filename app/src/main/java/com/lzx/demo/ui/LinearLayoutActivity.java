package com.lzx.demo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.lzx.demo.R;
import com.lzx.demo.base.ListBaseAdapter;
import com.lzx.demo.bean.ItemModel;
import com.lzx.demo.view.SampleFooter;
import com.lzx.demo.view.SampleHeader;

import java.util.ArrayList;

/**
 *
 * 带HeaderView、FooterView的LinearLayout RecyclerView
 */
public class LinearLayoutActivity extends AppCompatActivity {

    private LRecyclerView mRecyclerView = null;

    private DataAdapter mDataAdapter = null;

    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_ll_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (LRecyclerView) findViewById(R.id.list);

        //init data
        ArrayList<ItemModel> dataList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            ItemModel itemModel = new ItemModel();
            itemModel.title = "item" + i;
            dataList.add(itemModel);
        }

        mDataAdapter = new DataAdapter(this);
        mDataAdapter.setDataList(dataList);

        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mDataAdapter);
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //add a HeaderView
        View header = LayoutInflater.from(this).inflate(R.layout.sample_header,(ViewGroup)findViewById(android.R.id.content), false);

        mLRecyclerViewAdapter.addHeaderView(header);
        mLRecyclerViewAdapter.addHeaderView(new SampleHeader(this));

        SampleFooter sampleFooter = new SampleFooter(this);
        sampleFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 加载更多
                ArrayList<ItemModel> dataList = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    ItemModel itemModel = new ItemModel();
                    itemModel.title = "item" + (i + mDataAdapter.getItemCount());
                    dataList.add(itemModel);
                }
                mDataAdapter.addAll(dataList);
            }
        });


        //禁用下拉刷新功能
        mRecyclerView.setPullRefreshEnabled(false);

        //禁用自动加载更多功能
        mRecyclerView.setLoadMoreEnabled(false);

        //add a FooterView
        mLRecyclerViewAdapter.addFooterView(sampleFooter);

    }

    private class DataAdapter extends ListBaseAdapter<ItemModel> {

        private LayoutInflater mLayoutInflater;

        public DataAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(mLayoutInflater.inflate(R.layout.sample_item_text, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            String item = mDataList.get(position).title;

            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.textView.setText(item);
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            private TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.info_text);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

}