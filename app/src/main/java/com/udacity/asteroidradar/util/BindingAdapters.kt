package com.udacity.asteroidradar

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.data.PictureOfDay
import com.udacity.asteroidradar.ui.adapter.AsteroidAdapter
import com.udacity.asteroidradar.util.AsyncOperationState

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    val context = imageView.context
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription = context.getString(
            R.string.potentially_hazardous_asteroid_image
        )
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription = context.getString(
            R.string.not_hazardous_asteroid_image
        )
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    val context = imageView.context
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription = context.getString(
            R.string.potentially_hazardous_asteroid_image
        )
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = context.getString(
            R.string.not_hazardous_asteroid_image
        )
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
    textView.contentDescription =
        String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
    textView.contentDescription = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
    textView.contentDescription =
        String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("asteroidData")
fun bindRecyclerViewToDisplayAsteroidList(recyclerView: RecyclerView, data: List<Asteroid>?) {
    data.let { asteroids ->
        (recyclerView.adapter as AsteroidAdapter).run {
            submitList(asteroids)
        }
    }
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, imageObj: PictureOfDay?) {
    val context = imageView.context
    if (imageObj == null) return
    Picasso.with(imageView.context).load(imageObj.url).into(imageView)
    imageView.contentDescription = String.format(
        context.getString(
            R.string.nasa_picture_of_day_content_description_format
        ), imageObj.title
    )
}

@BindingAdapter("appLoading")
fun setLoadingVisibility(progressBar: ProgressBar, state: AsyncOperationState?) {
    progressBar.visibility = when (state) {
        AsyncOperationState.PENDING -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("frameVisibility")
fun bindFrameLayoutVisibility(frameLayout: FrameLayout, state: AsyncOperationState?) {
    frameLayout.visibility = when (state) {
        AsyncOperationState.PENDING -> View.GONE
        else -> View.VISIBLE
    }
}

@BindingAdapter("recycleViewVisibility")
fun bindRecycleViewVisibility(recyclerView: RecyclerView, state: AsyncOperationState?) {
    recyclerView.visibility = when (state) {
        AsyncOperationState.PENDING -> View.GONE
        else -> View.VISIBLE
    }
}