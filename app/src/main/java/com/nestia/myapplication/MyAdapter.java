package com.nestia.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VHItem> {

    private List<Integer> mIndexList = new ArrayList<>();

    @NonNull
    @Override
    public VHItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VHItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.VHItem holder, int position) {
        holder.setData(mIndexList.get(position));
    }

    @Override
    public int getItemCount() {
        return mIndexList.size();
    }

    public void update(int amount) {
        mIndexList.clear();

        for (int ii = 0; ii < amount; ii++) {
            mIndexList.add(ii);
        }
    }

    public void removeItem(int index) {
        mIndexList.remove(index);
        notifyItemRemoved(index);
    }

    static class VHItem extends RecyclerView.ViewHolder {
        private TextView tvItem;

        public VHItem(@NonNull View itemView) {
            super(itemView);

            tvItem = itemView.findViewById(R.id.tv_item);
        }

        public void setData(int digit) {
            tvItem.setText(String.valueOf(digit));
        }
    }

}
