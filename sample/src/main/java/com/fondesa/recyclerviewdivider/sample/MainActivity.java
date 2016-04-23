package com.fondesa.recyclerviewdivider.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fondesa.recyclerviewdivider.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int LIST_SIZE = 20000;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mFirstRecyclerView = (RecyclerView) findViewById(R.id.first_recycler_view);
        RecyclerView mSecondRecyclerView = (RecyclerView) findViewById(R.id.second_recycler_view);

        mFirstRecyclerView.addItemDecoration(new RecyclerViewDivider(Color.BLACK, 1));
        mSecondRecyclerView.addItemDecoration(new RecyclerViewDivider(Color.BLACK, 1));

        final List<Integer> dummyList = new ArrayList<>(LIST_SIZE);
        for (int i = 0; i < LIST_SIZE; i++) {
            dummyList.add(i + 1);
        }
        final DummyAdapter firstDummyAdapter = new DummyAdapter(true);
        mFirstRecyclerView.setAdapter(firstDummyAdapter);
        final DummyAdapter secondDummyAdapter = new DummyAdapter(false);
        mSecondRecyclerView.setAdapter(secondDummyAdapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                firstDummyAdapter.updateList(dummyList);
                secondDummyAdapter.updateList(dummyList);
            }
        }, 2000);
    }

    private class DummyAdapter extends RecyclerView.Adapter {
        private List<Integer> list;
        private boolean first;

        public DummyAdapter(boolean first) {
            this.list = new ArrayList<>();
            this.first = first;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DummyViewHolder(LayoutInflater.from(MainActivity.this).inflate(first ? R.layout.dummy_cell_first : R.layout.dummy_cell_second, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((DummyViewHolder) holder).setItemText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void updateList(List<Integer> list) {
            if (list != this.list) {
                this.list = list;
                notifyDataSetChanged();
            }
        }
    }

    private static class DummyViewHolder extends RecyclerView.ViewHolder {
        public DummyViewHolder(View itemView) {
            super(itemView);
        }

        public void setItemText(int item) {
            ((TextView) itemView).setText(String.valueOf(item));
        }
    }
}
