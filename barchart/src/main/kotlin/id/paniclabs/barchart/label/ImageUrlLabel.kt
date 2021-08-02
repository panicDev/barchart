package id.paniclabs.barchart.label

import android.view.View
import androidx.core.view.isVisible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_chart_item.view.*

data class ImageUrlLabel(val url: String) : Label {

    override fun render(view: View) = with(view) {
        Picasso.get().load(url).into(imgLabel)
        imgLabel.isVisible = true
        txtLabel.isVisible = false
    }
}