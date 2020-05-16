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

package com.fondesa.recyclerviewdivider.test

import androidx.recyclerview.widget.RecyclerView
import com.fondesa.recyclerviewdivider.Orientation

/**
 * @return [RecyclerView.VERTICAL] if this orientation is vertical or [RecyclerView.HORIZONTAL] if it's horizontal.
 */
val Orientation.recyclerViewOrientation: Int get() = if (isVertical) RecyclerView.VERTICAL else RecyclerView.HORIZONTAL
