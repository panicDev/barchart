# BarChart

## Dependency
```groovy
dependencies {
    implementation "id.paniclabs.barchart:barchart:0.0.1"
}
```

## Usage

```xml
<id.paniclabs.barchart:barchart.BarChart
        android:id="@+id/chart"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

```kotlin
chart.items = listOf(
                ChartItem(name = "First Item", value = 0.7f, additional = "", label = TextLabel("1")),
                ChartItem(name = "Second Item", value = 0.7f, additional = "", label = TextLabel("1")),
                ChartItem(name = "Third Item", value = 0.7f, additional = "", label = TextLabel("1")))
chart.itemClicks.subscribe { toast("${it.name} clicked") }
chart.itemLongClicks.subscribe { toast("${it.name} long clicked") }
```

## Customization

### XML attributes

| Attribute         | Type       | Default            | Example            |
|-------------------|:----------:|:------------------:|:------------------:|
|dividerColor       |color       |#BDBDBD             |@color/primary      |
|textColor          |color       |#FFFFFF             |@color/primary      |
|labelColor         |color       |#2196F3             |@color/primary      |
|labelTextColor     |color       |#FFFFFF             |@color/primary      |
|barColor           |color       |#03A9F4             |@color/primary      |
|selectedBarColor   |color       |barColor            |@color/primary      |
|unselectedBarColor |color       |#BDBDBD             |@color/primary      |
|selectable         |boolean     |false               |true                |
|visibleItems       |integer     |4                   |4                   |
|primaryFontFamily  |typeface    |default font        |@font/poppins_medium|
|secondaryFontFamily|typeface    |default font        |@font/poppins_bold  |

```xml
<id.paniclabs.barchart:barchart.BarChart
        android:id="@+id/chart"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:selectable="true"
        app:visibleItems="4"
        app:dividerColor="@android:color/darker_gray"
        app:textColor="@android:color/darker_gray"
        app:labelColor="@color/primaryDark"
        app:labelTextColor="@android:color/white"
        app:barColor="@android:color/primary"
        app:selectedBarColor="@android:color/primary"
        app:unselectedBarColor="@android:color/darker_gray"
        app:primaryFontFamily="@font/poppins_medium"
        app:secondaryFontFamily="@font/poppins_bold" />
```

### On the fly

##### All items
```kotlin
chart.selectable = true
chart.visibleItems = 4
chart.primaryTypeface = ResourcesCompat.getFont(context, R.font.poppins_medium)
chart.secondaryTypeface = ResourcesCompat.getFont(context, R.font.poppins_bold)
chart.dividerColor = ContextCompat.getColor(this, R.color.divider)
chart.textColor = ContextCompat.getColor(this, R.color.primaryText)
chart.labelColor = ContextCompat.getColor(this, R.color.primaryDark)
chart.labelTextColor = ContextCompat.getColor(this, R.color.secondaryText)
chart.barColor = ContextCompat.getColor(this, R.color.primary)
chart.selectedBarColor = ContextCompat.getColor(this, R.color.primary)
chart.unselectedBarColor = ContextCompat.getColor(this, R.color.divider)
```

#### Single item
```kotlin
ChartItem(name = "Item Name",
          value = 0.7f,
          label = ImageResLabel(R.drawable.ic_airplane),
          textColor = ContextCompat.getColor(this, R.color.primaryText),
          labelColor = ContextCompat.getColor(this, R.color.primaryDark),
          labelTextColor = ContextCompat.getColor(this, R.color.secondaryText),
          barColor = ContextCompat.getColor(this, R.color.primary),
          selectedBarColor = ContextCompat.getColor(this, R.color.primary),
          unselectedBarColor = ContextCompat.getColor(this, R.color.divider))
```