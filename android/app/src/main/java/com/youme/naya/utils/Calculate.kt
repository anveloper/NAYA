package com.youme.naya.utils

import android.util.DisplayMetrics

fun px2dp(px: Int): Int {
    return (px / (DisplayMetrics().densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

fun dp2px(dp: Int): Int {
    return (dp * (DisplayMetrics().densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}
