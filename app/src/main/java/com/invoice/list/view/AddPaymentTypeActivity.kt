package com.invoice.list.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import com.invoice.list.R
import com.invoice.list.databinding.ActivityAddPaymentTypeBinding
import com.invoice.list.extension.Constant
import com.invoice.list.extension.Constant.Companion.periodList
import com.invoice.list.extension.showToast
import com.invoice.list.model.PaymentType
import com.invoice.list.model.Period
import com.invoice.list.view.MainActivity.Companion.paymentTypeViewModel

class AddPaymentTypeActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAddPaymentTypeBinding

    private var selectedDay = Constant.WEEK_DAY
    private var selected = 0
    private var paymentType: PaymentType? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeView()
        initializeSpinner()
        changeListeners()
        initializeClick()

        intentControl()

    }

    @SuppressLint("SetTextI18n")
    private fun updateIsSaveControl() {


        if (paymentType != null && paymentType?.name?.isNotEmpty() == true) {

            binding.paymentTypeText.text = "Ödeme tipini güncelle"
            binding.saveBtn.text = "Güncelle"
            binding.deleteBtn.visibility = View.VISIBLE

            binding.editPaymentHeader.setText(paymentType!!.name)
            if (paymentType!!.period?.isNotEmpty() == true) {

                periodList.forEachIndexed { index, period ->
                    if (period.name == paymentType?.period) selected = index; return@forEachIndexed
                }

                binding.editPaymentPeriod.setSelection(selected)
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private fun intentControl() {
        paymentType = intent.getSerializableExtra("payment_type") as PaymentType?
        updateIsSaveControl()


    }

    private fun updatePaymentType() {
        val model = paymentType!!
        model.name = binding.editPaymentHeader.text.toString()
        model.periodDay = if (binding.editPaymentPeriodDay.text.toString()
                .isEmpty()
        ) null else binding.editPaymentPeriodDay.text.toString().toInt()
        model.period = if (binding.editPaymentPeriodDay.text.toString()
                .isEmpty()
        ) null else periodList[selected].name

        paymentTypeViewModel.updatePaymentTypeList(model)
        showToast("Ödeme tipi güncellendi")
        finish()
    }

    private fun savePaymentType(model: PaymentType) {
        paymentTypeViewModel.addPaymentTypeList(model)
        showToast("Ödeme tipi eklendi")
        finish()
    }

    private fun deletePaymentType(id: Int) {
        paymentTypeViewModel.deletePaymentTypeList(id)
        showToast("Ödeme tipi silindi")
        finish()

    }


    var s = false

    private fun initializeClick() {
        binding.saveBtn.setOnClickListener {
            if (binding.editPaymentHeader.text.toString().isNotEmpty()) {
                val model = PaymentType()
                model.name = binding.editPaymentHeader.text.toString()
                model.period = if (binding.editPaymentPeriodDay.text.toString()
                        .isEmpty()
                ) null else Constant.periodList[selected].name
                model.periodDay = if (binding.editPaymentPeriodDay.text.toString().isEmpty())
                    null
                else binding.editPaymentPeriodDay.text.toString().toInt()

                if (paymentType == null)
                    savePaymentType(model)
                else
                    updatePaymentType()

            } else
                showToast("Ödeme başlığı zorunlu alandır!")
        }

        binding.deleteBtn.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("Ödeme tipini silmek istiyor musunuz?")
                .setPositiveButton("Evet") { _, _ ->
                    deletePaymentType(paymentType?.id!!)
                    showToast("Ödeme tipi silindi!")
                }.setNegativeButton("Hayır", null)
                .show()

        }
    }


    private fun changeListeners() {
        binding.editPaymentPeriodDay.doAfterTextChanged {
            if (it?.length!! > 0) {
                val number = it.toString().toInt()
                if (number > selectedDay || number <= 0) {
                    binding.editPaymentPeriodDay.setText("")
                }
            }
        }
    }

    private fun initializeSpinner() {
        val adapter = ArrayAdapter<Period>(
            this,
           android.R.layout.select_dialog_item,
            Constant.periodList
        )
        binding.editPaymentPeriod.adapter = adapter

        binding.editPaymentPeriod.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    selected = position
                    binding.editPaymentPeriodDay.setText("")
                    selectedDay = when (position) {
                        0 -> Constant.WEEK_DAY
                        1 -> Constant.MONTH_DAY
                        else -> Constant.YEAR_DAY
                    }

                    if (paymentType?.periodDay != null && !s) {
                        s = true
                        binding.editPaymentPeriodDay.setText(paymentType?.periodDay.toString())
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
    }

    private fun initializeView() {
        binding = ActivityAddPaymentTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}