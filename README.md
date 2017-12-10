RecyclerViewDivider
===============
[![Build Status](https://travis-ci.org/Fondesa/RecyclerViewDivider.svg?branch=master)](https://travis-ci.org/Fondesa/RecyclerViewDivider)

A RecyclerView's divider that can be customized with simple properties or advanced ones.

<img src="https://raw.githubusercontent.com/Fondesa/RecyclerViewDivider/master/art/screenshot_div_simple.png" height="500">   <img src="https://raw.githubusercontent.com/Fondesa/RecyclerViewDivider/master/art/screenshot_grid.png" height="500">   <img src="https://raw.githubusercontent.com/Fondesa/RecyclerViewDivider/master/art/screenshot_div_draw.png" height="500">

It supports completely LinearLayoutManager and GridLayoutManager.

Usage
------

A basic version of the divider can be attached to a ```RecyclerView``` through this line:

```java
RecyclerViewDivider.with(context).build().addTo(recyclerView);
```

Each divider can be customized with various properties.
These properties can have a common value for every divider or a specific value related to a divider instance.

For further information, check the [wiki](https://github.com/Fondesa/RecyclerViewDivider/wiki).

Compatibility
------

**Android SDK**: RecyclerViewDivider requires a minimum API level of 14 (the same of RecyclerView).

Integration
------

You can download a jar from GitHub's [releases page](https://github.com/Fondesa/RecyclerViewDivider/releases) or grab it from ```jcenter()``` or ```mavenCentral()```.

### Gradle ###

```gradle
dependencies {
    compile 'com.github.fondesa:recycler-view-divider:1.4.1'
}
```

### Maven ###

```xml
<dependency>
  <groupId>com.github.fondesa</groupId>
  <artifactId>recycler-view-divider</artifactId>
  <version>1.4.1</version>
  <type>pom</type>
</dependency>
```