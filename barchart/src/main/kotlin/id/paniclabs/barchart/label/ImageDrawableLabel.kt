package id.paniclabs.barchart.label

import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.view_chart_item.view.*

data class ImageDrawableLabel(val drawable: Drawable) {

    fun render(view: View) = with(view) {
        imgLabel.setImageDrawable(drawable)
        imgLabel.isVisible = true
        txtLabel.isVisible = false
    }
}