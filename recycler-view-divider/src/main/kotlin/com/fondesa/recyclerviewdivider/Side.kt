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

import java.util.EnumMap
import java.util.EnumSet

/**
 * Identifies the sides of a rectangle.
 */
enum class Side {

    /**
     * The top side.
     */
    TOP,

    /**
     * The bottom side.
     */
    BOTTOM,

    /**
     * The left side in LTR.
     * The right side in RTL.
     */
    START,

    /**
     * The right side in LTR.
     * The left side in RTL.
     */
    END
}

/**
 * Identifies a set of [Side], internally implemented with an [EnumSet].
 */
typealias Sides = EnumSet<Side>

/**
 * Identifies a map using [Side] as the key, internally implemented with an [EnumMap].
 *
 * @param T the type of the value.
 */
internal typealias SidesMap<T> = EnumMap<Side, T>

/**
 * Initializes an empty set of sides.
 *
 * @return the empty [EnumSet] used to contain the sides.
 */
internal fun sidesOf(): Sides = Sides.noneOf(Side::class.java)

/**
 * Initializes an empty map using sides as keys.
 *
 * @return the empty [EnumMap] used to contain the [Side]-value pairs.
 */
internal fun <T> sidesMapOf(): SidesMap<T> = SidesMap(Side::class.java)
