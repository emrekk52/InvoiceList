package com.invoice.list.extension

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.invoice.list.R
import com.invoice.list.extension.Constant.Companion.MONTH_DAY
import com.invoice.list.extension.Constant.Companion.WEEK_DAY
import com.invoice.list.extension.Constant.Companion.YEAR_DAY
import com.invoice.list.extension.Constant.Companion.periodList
import com.invoice.list.model.PaymentType
import com.invoice.list.model.Period
import java.util.*


fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


// startIntent ile daha pratik geçiş ve veri göndermek
fun Activity.startIntent(
    targetActivity: Class<out Activity>,
    key: String? = null,
    value: Any? = null,
    isFinish: Boolean = false
) {

    val intent = Intent(this, targetActivity)
    if (key != null && value != null)
        when (value) {
            is Int -> intent.putExtra(key, value)
            is String -> intent.putExtra(key, value)
            is Boolean -> intent.putExtra(key, value)
            is PaymentType -> intent.putExtra(key, value)
        }
    this.startActivity(intent)

    if (isFinish)
        finish()

}