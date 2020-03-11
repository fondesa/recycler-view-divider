/*
 * Copyright (c) 2020 Fondesa
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

package com.fondesa.recyclerviewdivider.sample

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fondesa.recyclerviewdivider.RecyclerViewDivider
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    companion object {
        private const val LIST_SIZE = 16
        private const val SPAN_COUNT = 3
        private const val SHOW = "ALL"
        private const val REMOVE = "REMOVE"
    }

    private var dividerShown: Boolean = false

    private lateinit var firstDivider: RecyclerViewDivider
    private lateinit var secondDivider: RecyclerViewDivider

    private val firstRecyclerView by lazy { findViewById<RecyclerView>(R.id.first_recycler_view) }
    private val secondRecyclerView by lazy { findViewById<RecyclerView>(R.id.second_recycler_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstManager = firstRecyclerView.layoutManager as GridLayoutManager
        firstManager.spanCount = SPAN_COUNT
        firstManager.spanSizeLookup = DummyLookup()

        val secondManager = secondRecyclerView.layoutManager as GridLayoutManager
        secondManager.spanCount = 1
//        secondManager.spanSizeLookup = DummyLookup()

        firstDivider = RecyclerViewDivider.with(this)
            .size(24)
            .build()

        firstDivider.addTo(firstRecyclerView)

        secondDivider = RecyclerViewDivider.with(this)
            .color(Color.BLACK)
            .inset(0, 70)
            .build()

        secondDivider.addTo(secondRecyclerView)

        dividerShown = true

        val dummyList = (0 until LIST_SIZE).map { it + 1 }.toList()
        val firstDummyAdapter = DummyAdapter(true)
        firstRecyclerView.adapter = firstDummyAdapter
        val secondDummyAdapter = DummyAdapter(false)
        secondRecyclerView.adapter = secondDummyAdapter

        firstDummyAdapter.updateList(dummyList)
        secondDummyAdapter.updateList(dummyList)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_toggle_div).title = if (dividerShown) REMOVE else SHOW
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if (item.itemId == R.id.action_toggle_div) {
            if (dividerShown) {
                firstDivider.removeFrom(firstRecyclerView)
                secondDivider.removeFrom(secondRecyclerView)
            } else {
                firstDivider.addTo(firstRecyclerView)
                secondDivider.addTo(secondRecyclerView)
            }
            dividerShown = !dividerShown
            invalidateOptionsMenu()
        }
        return true
    }

    private inner class DummyAdapter internal constructor(private val first: Boolean) :
        RecyclerView.Adapter<DummyViewHolder>() {
        private var list: List<Int>? = null

        init {
            this.list = ArrayList()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DummyViewHolder =
            DummyViewHolder(
                LayoutInflater.from(this@MainActivity)
                    .inflate(
                        if (first) R.layout.dummy_cell_first else R.layout.dummy_cell_second,
                        parent,
                        false
                    )
            )

        override fun onBindViewHolder(holder: DummyViewHolder, position: Int) {
            holder.setItemText(list!![position])
        }

        override fun getItemCount(): Int = list!!.size

        internal fun updateList(list: List<Int>) {
            if (list !== this.list) {
                this.list = list
                notifyDataSetChanged()
            }
        }
    }

    private class DummyViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        internal fun setItemText(item: Int) {
            (itemView as TextView).text = item.toString()
        }
    }

    private class DummyLookup : GridLayoutManager.SpanSizeLookup() {

        override fun getSpanSize(position: Int): Int = position % 3 + 1
    }
}
