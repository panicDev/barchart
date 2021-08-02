package id.paniclabs.barchart

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.tomasznajda.simplerecyclerview.adapter.AdvancedSrvAdapter
import id.paniclabs.barchart.exception.SelectingDisabledException
import kotlinx.android.synthetic.main.view_chart_background.view.*
import kotlinx.android.synthetic.main.view_chart_foreground.view.*

class BarChart : FrameLayout {

    constructor(context: Context) : this(context, null)

    var clicks: ItemClickListener? = null

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
        inflate(R.layout.view_chart_background, attachToRoot = true)
        inflate(R.layout.view_chart_foreground, attachToRoot = true)
        attrs?.let { obtainAttrs(it) }
        chartRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        chartRecyclerView.adapter = adapter
        adapter.addViewHolder(ChartBundle::class, R.layout.view_chart_item) {
            ChartItemViewHolder(ChartItemView(context), clicks)
        }
    }

    @Suppress("UNCHECKED_CAST")
    var items: List<ChartItem> = emptyList()
        set(value) {
            field = value
            adapter.set(value.toBundleList())
            Log.i("CHART_LOG", "Adapter Size: ${adapter.itemCount}")
        }
    var visibleItems = 10
        set(value) {
            field = value
            config = config.copy(visibleItems = value)
            reload()
        }

    @ColorInt
    var dividerColor = 0
        set(value) {
            field = value
            config = config.copy(dividerColor = value)
            changeDividerColor(value)
        }

    @ColorInt
    var textColor = 0
        set(value) {
            field = value
            config = config.copy(textColor = value)
            reload()
        }

    @ColorInt
    var labelColor = 0
        set(value) {
            field = value
            config = config.copy(labelColor = value)
            reload()
        }

    @ColorInt
    var labelTextColor = 0
        set(value) {
            field = value
            config = config.copy(labelTextColor = value)
            reload()
        }

    @ColorInt
    var barColor = 0
        set(value) {
            field = value
            config = config.copy(barColor = value)
            reload()
        }

    @ColorInt
    var selectedBarColor = 0
        set(value) {
            field = value
            config = config.copy(selectedBarColor = value)
            reload()
        }

    @ColorInt
    var unselectedBarColor = 0
        set(value) {
            field = value
            config = config.copy(unselectedBarColor = value)
            reload()
        }
    var selectable = false
        set(value) {
            field = value
            config = config.copy(selectable = value)
            reload()
        }
    var primaryTypeface: Typeface? = null
        set(value) {
            field = value
            config = config.copy(primaryTypeface = value)
            refreshBackgroundTypeface(value)
            reload()
        }

    var secondaryTypeface: Typeface? = null
        set(value) {
            field = value
            config = config.copy(secondaryTypeface = value)
            reload()
        }

    var labelVisible = true
        set(value) {
            field = value
            config = config.copy(labelVisible = value)
            reload()
        }

    var textLabelVisible = true
        set(value) {
            field = value
            config = config.copy(labelTextVisible = value)
            reload()
        }

    var textVisible = true
        set(value) {
            field = value
            config = config.copy(textVisible = value)
            reload()
        }

    var selectedItem: ChartItem
        get() {
            if (selectable.not()) throw SelectingDisabledException()
            return (adapter.items.find { (it as ChartBundle).isSelected } as ChartBundle).item
        }
        set(value) = selectItem(value)

    val adapter = AdvancedSrvAdapter()

    //    private val clicks = PublishSubject.create<ChartItem>()
//    private val longClicks = PublishSubject.create<ChartItem>()
    private var config = ChartConfig(
        visibleItems,
        selectable,
        dividerColor,
        textColor,
        labelColor,
        labelTextColor,
        barColor,
        selectedBarColor,
        unselectedBarColor,
        primaryTypeface,
        secondaryTypeface,
        labelVisible,
        textLabelVisible,
        textVisible
    )

    fun reload() = adapter.set(items.toBundleList())

    private fun changeDividerColor(@ColorInt color: Int) {
        highestValue.setTextColor(color)
        lowestValue.setTextColor(color)
        (2 until lines.childCount)
            .forEach { index -> lines.getChildAt(index).setBackgroundColor(color) }
    }

    private fun refreshBackgroundTypeface(typeface: Typeface?) {
        highestValue.typeface = typeface
        lowestValue.typeface = typeface
    }

    private fun obtainAttrs(attrs: AttributeSet) {
        visibleItems = 10
        dividerColor = context.getColorCompat(R.color.divider)
        textColor = context.getColorCompat(R.color.text)
        labelColor = context.getColorCompat(R.color.label)
        labelTextColor = context.getColorCompat(R.color.text)
        barColor = context.getColorCompat(R.color.bar)
        selectedBarColor = barColor
        unselectedBarColor = context.getColorCompat(R.color.divider)
        selectable = false
        context.theme.obtainStyledAttributes(attrs, R.styleable.BarChart, 0, 0).apply {
            try {
                visibleItems = getInt(R.styleable.BarChart_visibleItems, visibleItems)
                dividerColor = getColor(R.styleable.BarChart_dividerColor, dividerColor)
                textColor = getColor(R.styleable.BarChart_textColor, textColor)
                labelColor = getColor(R.styleable.BarChart_labelColor, labelColor)
                labelTextColor = getColor(R.styleable.BarChart_labelTextColor, labelTextColor)
                barColor = getColor(R.styleable.BarChart_barColor, barColor)
                selectedBarColor = getColor(R.styleable.BarChart_selectedBarColor, selectedBarColor)
                unselectedBarColor =
                    getColor(R.styleable.BarChart_unselectedBarColor, unselectedBarColor)
                selectable = getBoolean(R.styleable.BarChart_selectable, selectable)
                primaryTypeface = getResourceId(R.styleable.BarChart_primaryFontFamily, -1)
                    .takeIf { it > 0 }
                    ?.let { ResourcesCompat.getFont(context, it) }
                secondaryTypeface = getResourceId(R.styleable.BarChart_secondaryFontFamily, -1)
                    .takeIf { it > 0 }
                    ?.let { ResourcesCompat.getFont(context, it) }
                    ?: primaryTypeface
            } finally {
                recycle()
            }
        }
    }

    private fun selectItem(chartItem: ChartItem) {
        if (selectable.not()) return
        val oldIndex = adapter.items.indexOfFirst { (it as ChartBundle).isSelected }
        val newIndex = adapter.items.indexOfFirst { (it as ChartBundle).item == chartItem }
        if (oldIndex > -1) changeItemSelection(oldIndex, false)
        changeItemSelection(newIndex, true)
    }

    private fun changeItemSelection(index: Int, selected: Boolean) =
        adapter.replace((adapter.items[index] as ChartBundle).copy(isSelected = selected), index)

    private fun List<ChartItem>.toBundleList() =
        mapIndexed { index, chartItem -> ChartBundle(chartItem, config, index == 0) }

    private fun Context.getColorCompat(@ColorRes colorResId: Int) =
        ContextCompat.getColor(this, colorResId)
}