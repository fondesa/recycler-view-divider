package com.nestia.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.fondesa.recyclerviewdivider.DividerBuilder;

public class MainActivity extends AppCompatActivity {

    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        new DividerBuilder(this).size(getResources().getDimensionPixelSize(R.dimen.divider_size))
                .asSpace()
                .build()
                .addTo(recyclerView);

        mAdapter = new MyAdapter();
        recyclerView.setAdapter(mAdapter);

        mAdapter.update(20);

        findViewById(R.id.btn_remove).setOnClickListener(view -> {
            mAdapter.removeItem(0);
        });
    }
}