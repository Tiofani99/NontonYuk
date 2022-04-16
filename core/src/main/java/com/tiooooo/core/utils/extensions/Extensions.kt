package com.tiooooo.core.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.tiooooo.core.R
import com.tiooooo.core.utils.InfoEnum
import com.tiooooo.core.utils.constant.Constant
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


fun String.toDate(format: String): Date {
    return try {
        val formatter = SimpleDateFormat(format, getIndonesianLocale())
        formatter.parse(this) ?: Date()
    } catch (e: Exception) {
        Timber.e("Failed to parse date")
        Date()
    }
}

fun getIndonesianLocale(): Locale {
    return Locale("en", "EN")
}

fun Date.toDateString(format: String): String {
    val formatter = SimpleDateFormat(format, getIndonesianLocale())
    return formatter.format(this) ?: ""
}

fun <T> Intent.putJSON(key: String, data: T): Intent {
    val jsonString = Gson().toJson(data)
    this.putExtra(key, jsonString)
    return this
}

fun <T> Intent.getJSON(key: String, cls: Class<T>): T? {
    val jsonString = this.getStringExtra(key)
    if (jsonString.isNullOrBlank()) {
        return null
    }

    return try {
        Gson().fromJson(jsonString, cls)
    } catch (e: Exception) {
        null
    }
}

fun String.createImageUrl(isOriginal: Boolean? = true): String =
    if (isOriginal == true) Constant.BASE_IMAGE_ORIGINAL + this
    else Constant.BASE_IMAGE_500 + this


fun Exception.getErrorMessage(): String {
    val defaultErrorMsg =
        "Unable to connect with server. Please check your connection or try again later."

    return when (this) {
        is HttpException -> {
            this.getErrorMessage()
        }
        is UnknownHostException -> {
            defaultErrorMsg
        }
        is SocketTimeoutException -> {
            defaultErrorMsg
        }
        is ConnectException -> {
            defaultErrorMsg
        }
        else -> {
            var message = this.message
            if (message.isNullOrBlank()) {
                message = "Unknown error."
            }
            message
        }
    }
}

fun HttpException.getErrorMessage(): String {
    var message = ""
    this.response()?.errorBody()?.string()?.let {
        try {
            val obj = JSONObject(it)
            if (message.isEmpty()) {
                if (obj.has("status_message")) {
                    message = obj.getString("status_message")
                }
            }
            if (message.isEmpty()) {
                message = it
            }
        } catch (e: JSONException) {
            message = it
        } catch (ex: Exception) {
            message = ex.getErrorMessage()
        }
    }
    if (message.isBlank()) {
        message = "Unknown error."
    }

    if (message.startsWith("[") && message.endsWith("]")) {
        try {
            val messageList = Gson().fromJson(message, mutableListOf<String>().javaClass)
            message = messageList.joinToString(", ")
        } catch (e: Exception) {
            Timber.i("failed to convert array as string")
        }
    }

    return message
}

fun View.showView() {
    visibility = View.VISIBLE
}

fun View.hideView() {
    visibility = View.GONE
}

fun Fragment.setCollapsing(
    title: String? = null,
    collapsingToolbar: CollapsingToolbarLayout,
    tvTitle: TextView,
    appbar: AppBarLayout,
) {
    collapsingToolbar.title = ""
    tvTitle.text = " "
    collapsingToolbar.setCollapsedTitleTextColor(
        ContextCompat.getColor(
            requireContext(),
            R.color.white
        )
    )

    appbar.setExpanded(true)
    appbar.addOnOffsetChangedListener(object :
        AppBarLayout.OnOffsetChangedListener {
        var isShow = false
        var scrollRange = -1


        override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
            val percentage =
                (appBarLayout!!.totalScrollRange - abs(n = verticalOffset).toFloat()) / appBarLayout.totalScrollRange

            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange
            }
            if (scrollRange + verticalOffset == 0) {
                collapsingToolbar.title = title
                tvTitle.text = title
                isShow = true
            } else if (isShow) {
                collapsingToolbar.title = " "
                tvTitle.text = " "
                isShow = false
            }
        }
    })

}

fun Fragment.getMessageStatus(title: String, favorite: Boolean) {
    val message = if (favorite) getString(R.string.success_add_favorite, title)
    else getString(R.string.success_delete_favorite, title)
    val type = if (favorite) InfoEnum.SUCCESS
    else InfoEnum.DANGER
    snackBar(message, type)
}

fun Context.getColorId(id: Int): Int = ContextCompat.getColor(this, id)

fun Fragment.snackBar(
    message: String?,
    type: InfoEnum,
    duration: Int = Snackbar.LENGTH_SHORT
): Snackbar? {
    return activity?.snackBar(message, type, duration)
}

fun Activity.snackBar(
    message: String?,
    type: InfoEnum,
    duration: Int = Snackbar.LENGTH_SHORT
): Snackbar? {
    if (message == null) return null
    val view = window.decorView.rootView.findViewById<View>(android.R.id.content)
    val sb = Snackbar.make(view, message, duration)
    sb.animationMode = Snackbar.ANIMATION_MODE_SLIDE
    sb.setBackgroundTint(
        getColorId(
            when (type) {
                InfoEnum.SUCCESS -> R.color.secondary
                InfoEnum.DANGER -> R.color.primary
            }
        )
    )
    sb.show()
    Timber.i("[SNACKBAR] $message")
    return sb
}