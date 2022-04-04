package com.invoice.list.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.datepicker.MaterialDatePicker
import com.invoice.list.databinding.ActivityAddPaymentBinding
import com.invoice.list.extension.showToast
import com.invoice.list.model.Payment
import com.invoice.list.model.PaymentType
import com.invoice.list.view.DetailActivity.Companion.paymentViewModel
import java.util.*

class AddPaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPaymentBinding
    private lateinit var todayDate: String
    private lateinit var calendar: Calendar
    private var paymentType: PaymentType? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeView()
        initializeTodayDate()
        initializeClick()
        intentControl()

    }

    private fun intentControl() {
        paymentType = intent.getSerializableExtra("payment_add") as PaymentType?
        supportActionBar?.title = paymentType?.name
    }

    private fun initializeTodayDate() {
        calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        setToday()
    }

    @SuppressLint("SetTextI18n")
    private fun setToday() {
        todayDate =
            "${if (calendar[Calendar.DAY_OF_MONTH] < 10) "0" + calendar[Calendar.DAY_OF_MONTH] else calendar[Calendar.DAY_OF_MONTH]}.${if (calendar[Calendar.MONTH] < 10) "0" + (calendar[Calendar.MONTH] + 1) else (calendar[Calendar.MONTH] + 1)}.${calendar[Calendar.YEAR]}"
        binding.date.text = "Ödeme tarihi: $todayDate"
    }

    private fun initializeClick() {

        binding.apply {

            btnAddPayment.setOnClickListener {
                if (binding.editPayment.text.toString().isNotEmpty()) {
                    savePayment()
                } else
                    showToast("Lütfen miktar giriniz!")
            }


            dateLayout.setOnClickListener {
                showCalendar()
            }


        }
    }

    private fun savePayment() {

        val model = Payment()
        model.date = todayDate
        model.amount = binding.editPayment.text.toString().toDouble()
        model.paymentTypeId = paymentType?.id

        paymentViewModel.addPaymentList(model)
        showToast("Ödeme eklendi!")
        finish()
    }

    private fun initializeView() {
        binding = ActivityAddPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun showCalendar() {

        val datePickerDialog = DatePickerDialog(this)
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.setTitle("Tarih seçiniz")
        datePickerDialog.setOnDateSetListener { datePicker, i, i2, i3 ->
            calendar.set(Calendar.DAY_OF_MONTH, datePicker.dayOfMonth)
            calendar.set(Calendar.MONTH, datePicker.month)
            calendar.set(Calendar.YEAR, datePicker.year)
            setToday()
        }
        datePickerDialog.show()


    }
}