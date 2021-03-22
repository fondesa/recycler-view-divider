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

package com.fondesa.recyclerviewdivider

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.MockRecyclerView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fondesa.recyclerviewdivider.test.context
import com.fondesa.recyclerviewdivider.test.linearLayoutManager
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.clearInvocations
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests of [BaseDividerItemDecoration].
 */
@RunWith(AndroidJUnit4::class)
class BaseDividerItemDecorationTest {
    private val getItemOffsets = mock<GetItemOffsets>()
    private val onDraw = mock<OnDraw>()
    private val onDataChanged = mock<() -> Unit>()

    @Test
    fun `addTo - decoration added to RecyclerView`() {
        val decoration = MockDecoration()
        val recyclerView = RecyclerView(context)

        assertEquals(0, recyclerView.itemDecorationCount)

        decoration.addTo(recyclerView)

        assertEquals(1, recyclerView.itemDecorationCount)
        assertEquals(decoration, recyclerView.getItemDecorationAt(0))
    }

    @Test
    fun `addTo - invoked multiple times - decoration added only once to RecyclerView`() {
        val decoration = MockDecoration()
        val recyclerView = RecyclerView(context)

        assertEquals(0, recyclerView.itemDecorationCount)

        decoration.addTo(recyclerView)
        decoration.addTo(recyclerView)
        decoration.addTo(recyclerView)
        decoration.addTo(recyclerView)

        assertEquals(1, recyclerView.itemDecorationCount)
        assertEquals(decoration, recyclerView.getItemDecorationAt(0))
    }

    @Test
    fun `removeFrom - decoration removed from RecyclerView`() {
        val decoration = MockDecoration()
        val recyclerView = RecyclerView(context)
        decoration.addTo(recyclerView)

        decoration.removeFrom(recyclerView)

        assertEquals(0, recyclerView.itemDecorationCount)
    }

    @Test
    fun `getItemOffsets - adapter not attached - overload not invoked`() {
        val recyclerView = RecyclerView(context)
        val decoration = MockDecoration().also { it.addTo(recyclerView) }
        val rect = Rect()

        decoration.getItemOffsets(rect, View(context), recyclerView, RecyclerView.State())

        assertEquals(Rect(), rect)
        verifyZeroInteractions(getItemOffsets)
    }

    @Test
    fun `getItemOffsets - adapter with no items - overload not invoked`() {
        val recyclerView = RecyclerView(context)
        val adapter = mock<RecyclerView.Adapter<*>> {
            on(it.itemCount) doReturn 0
        }
        recyclerView.adapter = adapter
        val decoration = MockDecoration().also { it.addTo(recyclerView) }
        val rect = Rect()

        decoration.getItemOffsets(rect, View(context), recyclerView, RecyclerView.State())

        assertEquals(Rect(), rect)
        verifyZeroInteractions(getItemOffsets)
    }

    @Test
    fun `getItemOffsets - layout manager not attached - overload not invoked`() {
        val recyclerView = RecyclerView(context)
        val adapter = mock<RecyclerView.Adapter<*>> {
            on(it.itemCount) doReturn 4
        }
        recyclerView.adapter = adapter
        val decoration = MockDecoration().also { it.addTo(recyclerView) }
        val rect = Rect()

        decoration.getItemOffsets(rect, View(context), recyclerView, RecyclerView.State())

        assertEquals(Rect(), rect)
        verifyZeroInteractions(getItemOffsets)
    }

    @Test
    fun `getItemOffsets - item view not in layout - overload not invoked`() {
        val itemView = View(context)
        val recyclerView = spy(RecyclerView(context)) {
            doReturn(RecyclerView.NO_POSITION).whenever(it).getChildAdapterPosition(itemView)
        }
        recyclerView.layoutManager = linearLayoutManager()
        val adapter = mock<RecyclerView.Adapter<*>> {
            on(it.itemCount) doReturn 4
        }
        recyclerView.adapter = adapter
        val decoration = MockDecoration().also { it.addTo(recyclerView) }
        val rect = Rect()

        decoration.getItemOffsets(rect, itemView, recyclerView, RecyclerView.State())

        assertEquals(Rect(), rect)
        verifyZeroInteractions(getItemOffsets)
    }

    @Test
    fun `getItemOffsets - item view in layout - overload invoked`() {
        val itemView = View(context)
        val recyclerView = spy(RecyclerView(context)) {
            doReturn(2).whenever(it).getChildAdapterPosition(itemView)
        }
        val layoutManager = linearLayoutManager()
        recyclerView.layoutManager = layoutManager
        val adapter = mock<RecyclerView.Adapter<*>> {
            on(it.itemCount) doReturn 4
        }
        recyclerView.adapter = adapter
        val decoration = MockDecoration().also { it.addTo(recyclerView) }
        val rect = Rect()

        decoration.getItemOffsets(rect, itemView, recyclerView, RecyclerView.State())

        assertEquals(Rect(), rect)
        verify(getItemOffsets).invoke(layoutManager = layoutManager, outRect = rect, itemView = itemView, itemCount = 4, itemIndex = 2)
    }

    @Test
    fun `getItemOffsets - deprecated - overload not invoked`() {
        val itemView = View(context)
        val recyclerView = spy(RecyclerView(context)) {
            doReturn(2).whenever(it).getChildAdapterPosition(itemView)
        }
        val layoutManager = linearLayoutManager()
        recyclerView.layoutManager = layoutManager
        val adapter = mock<RecyclerView.Adapter<*>> {
            on(it.itemCount) doReturn 4
        }
        recyclerView.adapter = adapter
        val decoration = MockDecoration().also { it.addTo(recyclerView) }
        val rect = Rect()

        @Suppress("DEPRECATION")
        decoration.getItemOffsets(rect, 2, recyclerView)

        assertEquals(Rect(), rect)
        verifyZeroInteractions(getItemOffsets)
    }

    @Test
    fun `onDraw - decoration is space - overload invoked`() {
        val recyclerView = RecyclerView(context)
        val decoration = MockDecoration(asSpace = true).also { it.addTo(recyclerView) }
        val canvas = mock<Canvas>()

        decoration.onDraw(canvas, recyclerView, RecyclerView.State())

        verifyZeroInteractions(canvas)
        verifyZeroInteractions(onDraw)
    }

    @Test
    fun `onDraw - adapter not attached - overload not invoked`() {
        val recyclerView = RecyclerView(context)
        val decoration = MockDecoration().also { it.addTo(recyclerView) }
        val canvas = mock<Canvas>()

        decoration.onDraw(canvas, recyclerView, RecyclerView.State())

        verifyZeroInteractions(canvas)
        verifyZeroInteractions(onDraw)
    }

    @Test
    fun `onDraw - adapter with no items, overload not invoked`() {
        val recyclerView = RecyclerView(context)
        val adapter = mock<RecyclerView.Adapter<*>> {
            on(it.itemCount) doReturn 0
        }
        recyclerView.adapter = adapter
        val decoration = MockDecoration().also { it.addTo(recyclerView) }
        val canvas = mock<Canvas>()

        decoration.onDraw(canvas, recyclerView, RecyclerView.State())

        verifyZeroInteractions(canvas)
        verifyZeroInteractions(onDraw)
    }

    @Test
    fun `onDraw - layout manager not attached - overload not invoked`() {
        val recyclerView = RecyclerView(context)
        val adapter = mock<RecyclerView.Adapter<*>> {
            on(it.itemCount) doReturn 4
        }
        recyclerView.adapter = adapter
        val decoration = MockDecoration().also { it.addTo(recyclerView) }
        val canvas = mock<Canvas>()

        decoration.onDraw(canvas, recyclerView, RecyclerView.State())

        verifyZeroInteractions(canvas)
        verifyZeroInteractions(onDraw)
    }

    @Test
    fun `onDraw - layout manager attached - overload invoked`() {
        val recyclerView = RecyclerView(context)
        val layoutManager = linearLayoutManager()
        recyclerView.layoutManager = layoutManager
        val adapter = mock<RecyclerView.Adapter<*>> {
            on(it.itemCount) doReturn 4
        }
        recyclerView.adapter = adapter
        val decoration = MockDecoration().also { it.addTo(recyclerView) }
        val canvas = mock<Canvas>()

        decoration.onDraw(canvas, recyclerView, RecyclerView.State())

        verifyZeroInteractions(canvas)
        verify(onDraw).invoke(canvas = canvas, recyclerView = recyclerView, layoutManager = layoutManager, itemCount = 4)
    }

    @Test
    fun `onDraw - deprecated - overload not invoked`() {
        val recyclerView = RecyclerView(context)
        val layoutManager = linearLayoutManager()
        recyclerView.layoutManager = layoutManager
        val adapter = mock<RecyclerView.Adapter<*>> {
            on(it.itemCount) doReturn 4
        }
        recyclerView.adapter = adapter
        val decoration = MockDecoration().also { it.addTo(recyclerView) }
        val canvas = mock<Canvas>()

        @Suppress("DEPRECATION")
        decoration.onDraw(canvas, recyclerView)

        verifyZeroInteractions(canvas)
        verifyZeroInteractions(onDraw)
    }

    @Test
    fun `onDataChanged - callback invoked`() {
        val recyclerView = spy(MockRecyclerView(context))
        val adapter = mock<RecyclerView.Adapter<*>>()
        recyclerView.adapter = adapter
        val dataObserver = argumentCaptor<RecyclerView.AdapterDataObserver>()
        val decoration = MockDecoration().also { it.addTo(recyclerView) }

        decoration.getItemOffsets(Rect(), View(context), recyclerView, RecyclerView.State())

        // Two times because the first time is a registration done by the Android framework.
        verify(adapter, times(2)).registerAdapterDataObserver(dataObserver.capture())
        verifyZeroInteractions(onDataChanged)
        clearInvocations(recyclerView)

        dataObserver.lastValue.onChanged()
        verify(onDataChanged).invoke()
        verify(recyclerView).markItemDecorInsetsDirty()
        dataObserver.lastValue.onItemRangeChanged(1, 1)
        verify(onDataChanged, times(2)).invoke()
        verify(recyclerView, times(2)).markItemDecorInsetsDirty()
        dataObserver.lastValue.onItemRangeChanged(1, 1, Any())
        verify(onDataChanged, times(3)).invoke()
        verify(recyclerView, times(3)).markItemDecorInsetsDirty()
        dataObserver.lastValue.onItemRangeInserted(1, 2)
        verify(onDataChanged, times(4)).invoke()
        verify(recyclerView, times(4)).markItemDecorInsetsDirty()
        dataObserver.lastValue.onItemRangeRemoved(3, 4)
        verify(onDataChanged, times(5)).invoke()
        verify(recyclerView, times(5)).markItemDecorInsetsDirty()
        dataObserver.lastValue.onItemRangeMoved(3, 4, 3)
        verify(onDataChanged, times(6)).invoke()
        verify(recyclerView, times(6)).markItemDecorInsetsDirty()
    }

    @Test
    fun `data observer - same registration until adapter changes`() {
        val recyclerView = RecyclerView(context)
        val adapter = mock<RecyclerView.Adapter<*>>()
        recyclerView.adapter = adapter
        val decoration = MockDecoration().also { it.addTo(recyclerView) }

        decoration.getItemOffsets(Rect(), View(context), recyclerView, RecyclerView.State())
        decoration.getItemOffsets(Rect(), View(context), recyclerView, RecyclerView.State())
        decoration.onDraw(Canvas(), recyclerView, RecyclerView.State())

        // Two times because the first time is a registration done by the Android framework.
        verify(adapter, times(2)).registerAdapterDataObserver(any())

        val adapter2 = mock<RecyclerView.Adapter<*>>()
        recyclerView.adapter = adapter2

        decoration.getItemOffsets(Rect(), View(context), recyclerView, RecyclerView.State())
        decoration.onDraw(Canvas(), recyclerView, RecyclerView.State())

        // Verifies it was not invoked anymore.
        verify(adapter, times(2)).registerAdapterDataObserver(any())
        // Two times because the first time is a registration done by the Android framework.
        verify(adapter2, times(2)).registerAdapterDataObserver(any())
    }

    @Test
    fun `attach state change listener - same registration until recycler view changes`() {
        val recyclerView = spy(RecyclerView(context))
        val decoration = MockDecoration().also { it.addTo(recyclerView) }

        decoration.getItemOffsets(Rect(), View(context), recyclerView, RecyclerView.State())
        decoration.getItemOffsets(Rect(), View(context), recyclerView, RecyclerView.State())
        decoration.onDraw(Canvas(), recyclerView, RecyclerView.State())

        verify(recyclerView).addOnAttachStateChangeListener(any())

        val recyclerView2 = spy(RecyclerView(context))
        decoration.getItemOffsets(Rect(), View(context), recyclerView2, RecyclerView.State())
        decoration.onDraw(Canvas(), recyclerView2, RecyclerView.State())

        verify(recyclerView).addOnAttachStateChangeListener(any())
        verify(recyclerView2).addOnAttachStateChangeListener(any())
    }

    @Test
    fun `recycler view detached - unregister observer, detach listener`() {
        val recyclerView = spy(RecyclerView(context))
        val adapter = mock<RecyclerView.Adapter<*>>()
        recyclerView.adapter = adapter
        val decoration = MockDecoration().also { it.addTo(recyclerView) }
        val attachStateChangeListener = argumentCaptor<View.OnAttachStateChangeListener>()
        val dataObserver = argumentCaptor<RecyclerView.AdapterDataObserver>()

        decoration.getItemOffsets(Rect(), View(context), recyclerView, RecyclerView.State())
        verify(recyclerView).addOnAttachStateChangeListener(attachStateChangeListener.capture())
        // Two times because the first time is a registration done by the Android framework.
        verify(adapter, times(2)).registerAdapterDataObserver(dataObserver.capture())

        attachStateChangeListener.lastValue.onViewAttachedToWindow(View(context))

        verify(recyclerView, never()).removeOnAttachStateChangeListener(attachStateChangeListener.lastValue)
        verify(adapter, never()).unregisterAdapterDataObserver(dataObserver.lastValue)

        attachStateChangeListener.lastValue.onViewDetachedFromWindow(View(context))

        verify(recyclerView).removeOnAttachStateChangeListener(attachStateChangeListener.lastValue)
        verify(adapter).unregisterAdapterDataObserver(dataObserver.lastValue)
    }

    private inner class MockDecoration(asSpace: Boolean = false) : BaseDividerItemDecoration(asSpace) {
        override fun getItemOffsets(
            layoutManager: RecyclerView.LayoutManager,
            outRect: Rect,
            itemView: View,
            itemCount: Int,
            itemIndex: Int
        ) = this@BaseDividerItemDecorationTest.getItemOffsets(layoutManager, outRect, itemView, itemCount, itemIndex)

        override fun onDraw(canvas: Canvas, recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager, itemCount: Int) =
            this@BaseDividerItemDecorationTest.onDraw(canvas, recyclerView, layoutManager, itemCount)

        override fun onDataChanged() {
            super.onDataChanged()
            this@BaseDividerItemDecorationTest.onDataChanged()
        }
    }

    // Simulates mock<Function5<RecyclerView.LayoutManager, Rect, View, Int, Int, Unit>>() since Mockito has issues when used with
    // Robolectric to mock a kotlin.FunctionN type.
    private interface GetItemOffsets {
        operator fun invoke(layoutManager: RecyclerView.LayoutManager, outRect: Rect, itemView: View, itemCount: Int, itemIndex: Int)
    }

    // Simulates mock<Function4<Canvas, RecyclerView, RecyclerView.LayoutManager, Int, Unit>>() since Mockito has issues when used with
    // Robolectric to mock a kotlin.FunctionN type.
    private interface OnDraw {
        operator fun invoke(canvas: Canvas, recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager, itemCount: Int)
    }
}
