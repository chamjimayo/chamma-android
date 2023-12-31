package com.umc.chamma.ui.qr

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import com.journeyapps.barcodescanner.BarcodeView
import com.journeyapps.barcodescanner.Size
import kotlin.math.roundToInt


class TopRectBarcodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BarcodeView(context, attrs, defStyleAttr) {


    override fun getFramingRectSize(): Size {
        return Size(Resources.getSystem().displayMetrics.widthPixels, dpToPx(212))
    }

    override fun calculateFramingRect(
        container: Rect?,
        surface: Rect?
    ): Rect {
        // create new rect instance that hold the container.
        val intersection = Rect(container)
        // specify the position of left direction.53
        intersection.left = dpToPx(53)
        // specify the position of top direction.240
        intersection.top = dpToPx(240)
        // specify the position of right direction.
        intersection.right =
            framingRectSize.width - dpToPx(53)
        // specify the position of bottom direction.275
        intersection.bottom =
            framingRectSize.height + dpToPx(275)
        return intersection
    }

    private fun dpToPx(dp: Int): Int {
        return ((dp * Resources.getSystem().displayMetrics.density).roundToInt())
    }
}