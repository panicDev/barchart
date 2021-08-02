package id.paniclabs.barchart.label

import android.view.View
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.view_chart_item.view.*

data class TextResLabel(@StringRes val textResId: Int) : Label {

    override fun render(view: View) = with(view) {
        txtLabel.setText(textResId)
        txtLabel.isVisible = true
        imgLabel.isVisible = false
    }
}