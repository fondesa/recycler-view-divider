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

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import com.fondesa.recyclerviewdivider.RecyclerViewDivider

class App : Application(), RecyclerViewDivider.BuilderProvider {

    override fun provideDividerBuilder(context: Context) = RecyclerViewDivider.Builder(context)
            .color(ContextCompat.getColor(context, R.color.divider_color))
            .size(context.resources.getDimensionPixelSize(R.dimen.divider_size))
}