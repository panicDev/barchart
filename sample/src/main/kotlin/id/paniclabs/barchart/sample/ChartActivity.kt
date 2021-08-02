package id.paniclabs.barchart.sample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import id.paniclabs.barchart.ChartItem
import id.paniclabs.barchart.ItemClickListener
import id.paniclabs.barchart.label.ImageResLabel
import id.paniclabs.barchart.label.TextLabel
import kotlinx.android.synthetic.main.activity_chart.*
import java.util.*
import kotlin.math.roundToInt

class ChartActivity : AppCompatActivity() {

    private val colors = listOf(R.color.primary,
            R.color.pink,
            R.color.purple,
            R.color.green,
            R.color.amber,
            R.color.orange,
            R.color.brown)

    private val images = listOf(R.drawable.ic_airplane,
            R.drawable.ic_person,
            R.drawable.ic_time,
            R.drawable.ic_tv)

    private val values = with(arrayListOf<Float>()) {
        val result = arrayListOf<Float>()
        for (i in 0 until 100) {
            result.add("12${i}0000.0".toFloat())
        }
        return@with result
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        prepareItems(boxImageLabels.isChecked, boxRandomizeColors.isChecked)

        chart.selectable = true
        chart.clicks = object : ItemClickListener {
            override fun onItemLongClick(item: ChartItem) {
                toast("${item.value} long clicked")
            }

            override fun onItemClick(item: ChartItem) {
                chart.selectedItem = item
//                toast("Clicked ${chart.selectedItem.value}")
            }

        }

        boxSelectable.setOnCheckedChangeListener { _, isChecked ->
            chart.selectable = isChecked
        }
        boxCustomFonts.setOnCheckedChangeListener { _, isChecked ->
            chart.primaryTypeface = if (isChecked) getFont(R.font.poppins_medium) else null
            chart.secondaryTypeface = if (isChecked) getFont(R.font.poppins_bold) else null
        }
        boxImageLabels.setOnCheckedChangeListener { _, isChecked ->
            prepareItems(boxImageLabels.isChecked, boxRandomizeColors.isChecked)
        }
        boxRandomizeColors.setOnCheckedChangeListener { _, _ ->
            prepareItems(boxImageLabels.isChecked, boxRandomizeColors.isChecked)
        }
    }

    private fun prepareItems(imageLabels: Boolean, randomizeColors: Boolean) {
        chart.labelVisible = false
        chart.textLabelVisible = false
        chart.textVisible = false
//        chart.items = (values.size downTo 0).map {
//            Log.i("VALUES_ITEM", "${it / 10f}")
//            ChartItem(
//                    name = "item $it",
//                    value = it / 10f,
//                    additional = "${it * 100}",
//                    label = if (imageLabels) ImageResLabel(randomImage()) else TextLabel("$it"),
//                    textColor = if (randomizeColors) randomColor() else null,
//                    labelColor = if (randomizeColors) randomColor() else null,
//                    labelTextColor = if (randomizeColors) randomColor() else null,
//                    barColor = if (randomizeColors) randomColor() else null,
//                    selectedBarColor = if (randomizeColors) randomColor() else null,
//                    unselectedBarColor = if (randomizeColors) randomColor() else null)
//        }
//

        val cl = (values.size downTo 1).map { it }.shuffled()
        val chartItemList = arrayListOf<ChartItem>()
        for ((index, it) in values.shuffled().withIndex()) {
            chartItemList.add(
                    ChartItem(
                            name = "item ${it.roundToInt()}",
                            value = cl[index] / values.size.toFloat(),
                            additional = "${it * 100}",
                            label = if (imageLabels) ImageResLabel(randomImage()) else TextLabel("$it"),
                            textColor = if (randomizeColors) randomColor() else null,
                            labelColor = if (randomizeColors) randomColor() else null,
                            labelTextColor = if (randomizeColors) randomColor() else null,
                            barColor = if (randomizeColors) randomColor() else ContextCompat.getColor(this, R.color.primary),
                            selectedBarColor = if (randomizeColors) randomColor() else ContextCompat.getColor(this, R.color.primary),
                            unselectedBarColor = if (randomizeColors) randomColor() else ContextCompat.getColor(this, R.color.pink))
            )
        }
        chart.items = chartItemList

    }

    private fun randomImage() = images[Random().nextInt(images.size)]

    private fun randomColor() = ContextCompat.getColor(this, colors[Random().nextInt(colors.size)])

    private fun toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

    private fun getFont(@FontRes id: Int) = ResourcesCompat.getFont(this, id)
}