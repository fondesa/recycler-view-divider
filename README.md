RecyclerViewDivider
===============
[![Build Status](https://travis-ci.com/fondesa/recycler-view-divider.svg?branch=master)](https://travis-ci.com/fondesa/recycler-view-divider)

A RecyclerView's divider that can be customized with simple properties or advanced ones.

<img src="https://raw.githubusercontent.com/fondesa/recycler-view-divider/master/art/screenshot_div_simple.png" height="500">   <img src="https://raw.githubusercontent.com/fondesa/recycler-view-divider/master/art/screenshot_grid.png" height="500">   <img src="https://raw.githubusercontent.com/fondesa/recycler-view-divider/master/art/screenshot_div_draw.png" height="500">

It supports:
- `LinearLayoutManager` (completely)
- `GridLayoutManager` (completely)
- `StaggeredGridLayoutManager` (partially)

Usage
------

A basic version of the divider can be attached to a ```RecyclerView``` through this line:

```java
RecyclerViewDivider.with(context).build().addTo(recyclerView);
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

```gradle
dependencies {
    compile 'com.github.fondesa:recycler-view-divider:3.1.0'
}
```

### Maven ###

```xml
<dependency>
  <groupId>com.github.fondesa</groupId>
  <artifactId>recycler-view-divider</artifactId>
  <version>3.1.0</version>
  <type>pom</type>
</dependency>
```