package com.fondesa.recyclerviewdivider.factory;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.fondesa.recycler_view_divider.R;

/**
 * Created by antoniolig on 03/05/2016.
 */
public abstract class SizeFactory {
    private static Default defaultFactory;

    public static synchronized SizeFactory getDefault(@NonNull Context context) {
        if (defaultFactory == null) {
            defaultFactory = new Default(context);
        }
        return defaultFactory;
    }

    public abstract int sizeForItem(Drawable drawable, int orientation, int listSize, int position);

    private static class Default extends SizeFactory {
        private int defaultSize;

        Default(Context context) {
            defaultSize = context.getResources().getDimensionPixelSize(R.dimen.recycler_view_divider_size);
        }

        @Override
        public int sizeForItem(Drawable drawable, int orientation, int listSize, int position) {
            int size = (orientation == RecyclerView.VERTICAL) ? drawable.getIntrinsicHeight() : drawable.getIntrinsicWidth();
            if (size == -1) {
                size = defaultSize;
            }
            return size;
        }
    }
}
