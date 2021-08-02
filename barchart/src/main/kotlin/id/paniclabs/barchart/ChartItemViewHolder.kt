package id.paniclabs.barchart

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.util.Log
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.tomasznajda.simplerecyclerview.SrvViewHolder
import kotlinx.android.synthetic.main.view_chart_item.view.*

internal class ChartItemViewHolder(
    itemView: View,
    private val clicks: ItemClickListener?
) : RecyclerView.ViewHolder(itemView), SrvViewHolder<ChartBundle> {


    @SuppressLint("SetTextI18n")
    override fun bind(item: ChartBundle) {
        Log.i("CHART_LOG", item.item.toString())
        with(itemView as ChartItemView) {
            visibleItems = item.config.visibleItems
            percentage = item.item.value
            name.text = item.item.name
            percent.text = String.format("%.0f", item.item.value * 100) + "%"
            additional.text = item.item.additional
            item.item.label.render(label)

            percent.isInvisible = true
            additional.isInvisible = true
            name.isVisible = item.config.textVisible

            label.isVisible = item.config.labelVisible

            txtLabel.isVisible = item.config.labelTextVisible

            name.setTextColor(item.item.textColor ?: item.config.textColor)
            percent.setTextColor(item.item.textColor ?: item.config.textColor)
            additional.setTextColor(item.item.textColor ?: item.config.textColor)
            txtLabel.setTextColor(item.item.labelTextColor ?: item.config.labelTextColor)
            progress.setBackgroundColor(item.getBarColor())
            label.setBackgroundTintColor(item.item.labelColor ?: item.config.labelColor)

            name.typeface = item.config.primaryTypeface
            percent.typeface = item.config.primaryTypeface
            additional.typeface = item.config.secondaryTypeface
            txtLabel.typeface = item.config.secondaryTypeface

            progressLayout.setOnClickListener {
                clicks?.onItemClick(item.item)
            }
            progressLayout.setOnLongClickListener { clicks?.onItemLongClick(item.item); true }
        }
    }

    private fun ChartBundle.getBarColor() = when {
        config.selectable && isSelected -> item.selectedBarColor ?: config.selectedBarColor
        config.selectable && isSelected.not() -> item.unselectedBarColor
            ?: config.unselectedBarColor
        else -> item.barColor ?: config.barColor
    }

    private fun View.setBackgroundTintColor(@ColorInt color: Int) =
        ViewCompat.setBackgroundTintList(this, ColorStateList.valueOf(color))
}