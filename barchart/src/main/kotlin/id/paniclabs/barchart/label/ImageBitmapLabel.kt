package id.paniclabs.barchart.label

import android.graphics.Bitmap
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.view_chart_item.view.*

data class ImageBitmapLabel(val bitmap: Bitmap) : Label {

    override fun render(view: View) = with(view) {
        imgLabel.setImageBitmap(bitmap)
        imgLabel.isVisible = true
        txtLabel.isVisible = false
    }
}