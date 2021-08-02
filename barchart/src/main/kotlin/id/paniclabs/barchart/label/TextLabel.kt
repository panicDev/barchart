package id.paniclabs.barchart.label

import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.view_chart_item.view.*

data class TextLabel(val text: String) : Label {

    override fun render(view: View) = with(view) {
        txtLabel.text = text
        txtLabel.isVisible = true
        imgLabel.isVisible = false
    }
}