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

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.fondesa.recyclerviewdivider.DividerBuilder;
import com.fondesa.recyclerviewdivider.RecyclerViewStaggeredDivider;
import com.fondesa.recyclerviewdivider.StaggeredDividerBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TestAdapter mAdapter;
    private List<Bean> mBeans = new ArrayList<>();
    private static final String[] TITLES = new String[]{
            "1\t\t\t\t\t\t\t\t\t",
            "2",
            "3",
            "4"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.rv);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, RecyclerView.HORIZONTAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(layoutManager);
//        RecyclerViewDivider.with(recyclerView.getContext())
//                .size(40)
//                .color(Color.RED)
//                .build().addTo(recyclerView);
        RecyclerViewStaggeredDivider.builder(recyclerView.getContext())
                .size(1)
                .color(Color.RED)
                .build().addTo(recyclerView);
        addDatas();
        mAdapter = new TestAdapter(mBeans);
        recyclerView.setAdapter(mAdapter);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDatas();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addDatas() {
        for (int i = 0; i < TITLES.length; i++) {
            final Bean bean = new Bean();
//            int num = (int) (Math.random() * 10 + 1);
            final String img = TITLES[i];
            bean.setTitle(img);
            mBeans.add(bean);
        }
    }
}
