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

/**
 * Identifies the orientation of a component (e.g. a grid, a divider, etc...).
 */
enum class Orientation {

    /**
     * Identifies a vertical component.
     */
    VERTICAL,

    /**
     * Identifies an horizontal component
     */
    HORIZONTAL;

    /**
     * @return true if the component is vertical, false otherwise.
     */
    val isVertical: Boolean get() = this == VERTICAL

    /**
     * @return true if the component is horizontal, false otherwise.
     */
    val isHorizontal: Boolean get() = this == HORIZONTAL
}
