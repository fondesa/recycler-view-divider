package com.fondesa.recyclerviewdivider.sample;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fondesa.recyclerviewdivider.RecyclerViewDivider;
import com.fondesa.recyclerviewdivider.factories.MarginFactory;
import com.fondesa.recyclerviewdivider.factories.SizeFactory;
import com.fondesa.recyclerviewdivider.factories.TintFactory;
import com.fondesa.recyclerviewdivider.factories.VisibilityFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int LIST_SIZE = 18;
    private static final int SPAN_COUNT = 2;
    private static final String SHOW = "ALL";
    private static final String REMOVE = "REMOVE";
    boolean dividerShown;

    private RecyclerViewDivider firstDivider;
    private RecyclerViewDivider secondDivider;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mFirstRecyclerView = (RecyclerView) findViewById(R.id.first_recycler_view);
        ((GridLayoutManager) mFirstRecyclerView.getLayoutManager()).setSpanCount(SPAN_COUNT);

        RecyclerView mSecondRecyclerView = (RecyclerView) findViewById(R.id.second_recycler_view);
        ((GridLayoutManager) mSecondRecyclerView.getLayoutManager()).setSpanCount(SPAN_COUNT);

        firstDivider = RecyclerViewDivider.with(this)
                .addTo(mFirstRecyclerView)
                .visibilityFactory(new VisibilityFactory() {
                    @Override
                    public int displayDividerForItem(int groupCount, int groupIndex) {
                        if (groupIndex == groupCount - 1) {
                            return SHOW_ITEMS_ONLY;
                        }
                        return SHOW_ALL;
                    }
                })
//                .sizeFactory(new SizeFactory() {
//                    @Override
//                    public int sizeForItem(@Nullable Drawable drawable, int orientation, int listSize, int position) {
//                        return position % 2 == 0 ? 40 : 10;
//                    }
//                })
//                .tintFactory(new TintFactory() {
//                    @Override
//                    public int tintForItem(int listSize, int position) {
//                        return position % 3 == 0 ? Color.RED : Color.GREEN;
//                    }
//                })
                .color(Color.RED)
//                .size(getResources().getDimensionPixelSize(R.dimen.first_div_size))
                .size(20)
//                .marginSize(20)
                .build();

        firstDivider.attach();

        secondDivider = RecyclerViewDivider.with(this)
                .addTo(mSecondRecyclerView)
                .visibilityFactory(new VisibilityFactory() {
                    @Override
                    public int displayDividerForItem(int groupCount, int groupIndex) {
//                        if (groupIndex == groupCount - 1) {
//                            return SHOW_ITEMS_ONLY;
//                        }
                        return SHOW_ALL;
                    }
                })
//                .sizeFactory(new SizeFactory() {
//                    @Override
//                    public int sizeForItem(@Nullable Drawable drawable, int orientation, int listSize, int position) {
//                        return position % 2 == 0 ? 40 : 10;
//                    }
//                })
//                .tintFactory(new TintFactory() {
//                    @Override
//                    public int tintForItem(int listSize, int position) {
//                        return position % 3 == 0 ? Color.RED : Color.GREEN;
//                    }
//                })
                .color(Color.RED)
                .size(20)
                .marginSize(20)
                .build();

        secondDivider.attach();

        dividerShown = true;

        final List<Integer> dummyList = new ArrayList<>(LIST_SIZE);
        for (int i = 0; i < LIST_SIZE; i++) {
            dummyList.add(i + 1);
        }
        final DummyAdapter firstDummyAdapter = new DummyAdapter(true);
        mFirstRecyclerView.setAdapter(firstDummyAdapter);
        final DummyAdapter secondDummyAdapter = new DummyAdapter(false);
        mSecondRecyclerView.setAdapter(secondDummyAdapter);

        firstDummyAdapter.updateList(dummyList);
        secondDummyAdapter.updateList(dummyList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_toggle_div).setTitle(dividerShown ? REMOVE : SHOW);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.action_toggle_div) {
            if (dividerShown) {
                firstDivider.detach();
                secondDivider.detach();
            } else {
                firstDivider.attach();
                secondDivider.attach();
            }
            dividerShown = !dividerShown;
            invalidateOptionsMenu();
        }
        return true;
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
