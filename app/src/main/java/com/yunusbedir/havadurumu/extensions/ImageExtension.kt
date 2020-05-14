package com.yunusbedir.havadurumu.extensions

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yunusbedir.havadurumu.R


/**
 * Created by YUNUS BEDİR on 14.05.2020.
 */

fun View.extSetBackGround(main: String?) {
    this.setBackgroundResource(
        when (main) {
            null -> {
                R.drawable.day_clearsky
            }
            "" -> {
                R.drawable.day_clearsky
            }
            "clear" -> {
                R.drawable.day_clearsky
            }
            "rain" -> {
                R.drawable.day_rain
            }
            "snow" -> {
                R.drawable.day_snow
            }
            "clouds" -> {
                R.drawable.day_cloudy
            }
            else -> {
                R.drawable.day_clearsky
            }
        }
    )
}

fun ImageView.extImageLoad(url: String) {
    Log.i("Extension", url)

    val options = RequestOptions()
        .placeholder(placeHolderProgressBar(context))
        .error(R.mipmap.ic_launcher_round)


    Glide.with(this)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

fun placeHolderProgressBar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 4f
        centerRadius = 40f
        start()
    }
}