RecyclerViewDivider
===============
[![Build Status](https://travis-ci.com/fondesa/recycler-view-divider.svg?branch=master)](https://travis-ci.com/fondesa/recycler-view-divider)

A RecyclerView's divider which can be customized with simple properties or advanced ones.

<img src="https://raw.githubusercontent.com/fondesa/recycler-view-divider/master/art/screenshot_linear.png" height="500">   <img src="https://raw.githubusercontent.com/fondesa/recycler-view-divider/master/art/screenshot_grid.png" height="500">   <img src="https://raw.githubusercontent.com/fondesa/recycler-view-divider/master/art/screenshot_staggered.png" height="500">

It supports:
- `LinearLayoutManager`
- `GridLayoutManager`
- `StaggeredGridLayoutManager`

Usage
------

A basic version of the divider can be attached to a ```RecyclerView``` in these ways:

```kotlin
// Default configuration.
recyclerView.addDivider()
```
**OR**
```kotlin
// Custom configuration.
context.dividerBuilder()
    // [...]
    .build()
    .addTo(recyclerView)
```

Each divider can be customized with various properties.
These properties can have a common value for every divider or a specific value related to a divider instance.

For further information, check the [wiki](https://github.com/fondesa/recycler-view-divider/wiki).

Compatibility
------

**Android SDK**: RecyclerViewDivider requires a minimum API level of 14 (the same of RecyclerView).

**AndroidX**: this library requires AndroidX. To use it in a project without AndroidX, refer to the version *2.x*

Integration
------

You can download a jar from GitHub's [releases page](https://github.com/fondesa/recycler-view-divider/releases) or grab it from ```jcenter()``` or ```mavenCentral()```.

### Gradle ###

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.fondesa/recycler-view-divider/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.fondesa/recycler-view-divider) 

```gradle
dependencies {
    implementation 'com.github.fondesa:recycler-view-divider:x.x.x'
}
```