package com.example.mweatherappmvvm.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mweatherapplication.BuildConfig
import com.example.mweatherapplication.R
import com.example.mweatherappmvvm.common.AppConstants.ICON_EXTENSION_URL


@BindingAdapter("weatherIcon")
fun bindIcon(view: ImageView, icon: String?) {
    icon?.let {
        Glide.with(view.context)
            .load(BuildConfig.WEATHER_ICON_URL + icon + ICON_EXTENSION_URL)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("inDegreeCelcius")
fun bindTextViewTempearture(view: TextView, tempInDegreeCelcius: String?) {
    tempInDegreeCelcius?.let {
        view.text = view.context.getString(R.string.degreeCeclius, tempInDegreeCelcius.trim())
    }
}
@BindingAdapter("isVisible")
fun bindTextViewVisisbility(view: TextView, data:String?) {
   view.isVisible = !data.isNullOrBlank()
}