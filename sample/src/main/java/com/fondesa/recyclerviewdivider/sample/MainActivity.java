package com.fondesa.recyclerviewdivider.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fondesa.recyclerviewdivider.RecyclerViewDivider;

public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mFirstRecyclerView = (RecyclerView) findViewById(R.id.first_recycler_view);
        RecyclerView mSecondRecyclerView = (RecyclerView) findViewById(R.id.second_recycler_view);

        mFirstRecyclerView.addItemDecoration(new RecyclerViewDivider(Color.BLACK, 1));
        mSecondRecyclerView.addItemDecoration(new RecyclerViewDivider(Color.BLACK, 1));

        final int[] dummyArray = new int[20];
        for (int i = 0; i < dummyArray.length; i++) {
            dummyArray[i] = i + 1;
        }
        final DummyAdapter firstDummyAdapter = new DummyAdapter(dummyArray, true);
        mFirstRecyclerView.setAdapter(firstDummyAdapter);
        final DummyAdapter secondDummyAdapter = new DummyAdapter(dummyArray, false);
        mSecondRecyclerView.setAdapter(secondDummyAdapter);
    }

    private class DummyAdapter extends RecyclerView.Adapter {
        private int[] array;
        private boolean first;

        public DummyAdapter(int[] array, boolean first) {
            this.array = array;
            this.first = first;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DummyViewHolder(LayoutInflater.from(MainActivity.this).inflate(first ? R.layout.dummy_cell_first : R.layout.dummy_cell_second, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((DummyViewHolder) holder).setItemText(array[position]);
        }

        @Override
        public int getItemCount() {
            return array.length;
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
