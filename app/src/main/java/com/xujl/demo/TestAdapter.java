/*
 * Copyright (c) 2020 Giorgio Antonioli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xujl.demo;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class TestAdapter extends BaseQuickAdapter<Bean, BaseViewHolder> {
    public TestAdapter(@Nullable List<Bean> data) {
        super(R.layout.item_test, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Bean item) {
        if (item.getTitle().equals("2")) {
//            ((StaggeredGridLayoutManager.LayoutParams) helper.itemView.getLayoutParams()).setFullSpan(true);
        }
        helper.setText(R.id.tv, item.getTitle());
        if (helper.getView(R.id.tv).getTag() == null || !helper.getView(R.id.tv).getTag().equals(item.getTitle())) {
            helper.getView(R.id.tv).setTag(item.getTitle());
            ((TextView) helper.getView(R.id.tv)).setText(item.getTitle());
        }

    }
}
