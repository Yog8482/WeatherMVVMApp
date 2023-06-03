package com.example.mweatherappmvvm.utils

import java.math.RoundingMode
import java.text.DecimalFormat
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AlertDialog
import com.example.mweatherapplication.R


fun Double.ceilTo2DecimalDigit() {

    val df = DecimalFormat("#.#")
    df.roundingMode = RoundingMode.CEILING
    df.format(this)

}

val Context.isConnected: Boolean
    get() {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                val nw = connectivityManager.activeNetwork ?: return false
                val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
                when {
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }

            else -> {
                // Use depreciated methods only on older devices
                val nwInfo = connectivityManager.activeNetworkInfo ?: return false
                nwInfo.isConnected
            }
        }
    }


fun Context.showGenericAlertDialog(message: String) {
    val dialog = AlertDialog.Builder(this).apply {
        setMessage(message)
        setPositiveButton(getString(R.string.button_text_ok)) { dialog, _ ->
            dialog.dismiss()
        }
    }.create()

    if (dialog.isShowing) {
        dialog.setMessage(message)
    } else {
        dialog.show()
    }
}