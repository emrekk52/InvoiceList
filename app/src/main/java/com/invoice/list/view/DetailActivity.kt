package com.invoice.list.view

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.invoice.list.adapter.PaymentAdapter
import com.invoice.list.databinding.ActivityDetailBinding
import com.invoice.list.databinding.ActivityMainBinding
import com.invoice.list.extension.showToast
import com.invoice.list.extension.startIntent
import com.invoice.list.model.Payment
import com.invoice.list.model.PaymentType
import com.invoice.list.viewmodel.PaymentViewModel
import com.invoice.list.viewmodel.PaymentViewModelFactory
import java.text.FieldPosition

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var paymentType: PaymentType
    private lateinit var paymentList: List<Payment>

    companion object {
        lateinit var paymentViewModel: PaymentViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeView()
        initializeClick()
        intentControl()
        initializeViewModel()

    }

    private fun initializeViewModel() {
        paymentViewModel = ViewModelProvider(
            this,
            PaymentViewModelFactory(application, paymentType.id!!)
        ).get(PaymentViewModel::class.java)

        paymentViewModel.paymentList.observe(this) {
            paymentList = it
            val adapter = PaymentAdapter(it, ::paymentClickItem)
            binding.recyclerPaymentHistory.adapter = adapter
        }
    }

    private fun paymentClickItem(position: Int) {
        deletePayment(paymentList[position])
    }

    private fun deletePayment(payment: Payment) {

        AlertDialog.Builder(this)
            .setTitle("Ödemeyi silmek istiyor musunuz?")
            .setPositiveButton("Evet") { _, _ ->
                paymentViewModel.deletePaymentList(payment.id!!)
                showToast("Ödeme silindi!")
            }.setNegativeButton("Hayır", null)
            .show()

    }

    @SuppressLint("SetTextI18n")
    private fun intentControl() {

        paymentType = intent.getSerializableExtra("payment_detail") as PaymentType
        supportActionBar?.title = paymentType.name
        binding.period.text =
            "Ödeme periyodu: " + if (paymentType.period == null) "Boş" else paymentType.period
        binding.periodDay.text =
            "Ödeme günü: " + if (paymentType.periodDay == 0) "Boş" else "${paymentType.periodDay}. gün"
    }

    private fun initializeClick() {
        binding.apply {


            btnEdit.setOnClickListener {

                this@DetailActivity.startIntent(
                    targetActivity = AddPaymentTypeActivity::class.java,
                    key = "payment_type",
                    value = paymentType,
                    isFinish = true
                )

            }

            btnAddPayment.setOnClickListener {
                this@DetailActivity.startIntent(
                    AddPaymentActivity::class.java,
                    "payment_add",
                    paymentType
                )
            }
        }
    }

    private fun initializeView() {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}