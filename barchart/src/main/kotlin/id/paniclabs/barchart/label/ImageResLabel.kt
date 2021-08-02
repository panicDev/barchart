package id.paniclabs.barchart.label

import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.view_chart_item.view.*

data class ImageResLabel(@DrawableRes val imageResId: Int) : Label {

    override fun render(view: View) = with(view) {
        imgLabel.setImageResource(imageResId)
        imgLabel.isVisible = true
        txtLabel.isVisible = false
    }
}