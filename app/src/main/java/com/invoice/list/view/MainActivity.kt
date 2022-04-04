package com.invoice.list.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.invoice.list.adapter.PaymentTypeAdapter
import com.invoice.list.databinding.ActivityMainBinding
import com.invoice.list.extension.startIntent
import com.invoice.list.model.PaymentType
import com.invoice.list.viewmodel.PaymentTypeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        lateinit var paymentTypeViewModel: PaymentTypeViewModel
    }

    private lateinit var paymentTypeList: List<PaymentType>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeView()

        initializeViewModel()

        initializeClick()
    }


    private fun initializeViewModel() {
        paymentTypeViewModel = ViewModelProvider(this).get(PaymentTypeViewModel::class.java)

        paymentTypeViewModel.paymentTypeList.observe(this@MainActivity) {
            paymentTypeList = it
            val adapter = PaymentTypeAdapter(it, ::paymentClickItem, ::paymentClickButtonItem)
            binding.paymentTypeRecycler.adapter = adapter
            binding.paymentTypeRecycler.layoutManager = LinearLayoutManager(this)
        }

    }

    private fun initializeClick() {

        binding.apply {


            addNewPaymentType.setOnClickListener {
                val intent = Intent(this@MainActivity, AddPaymentTypeActivity::class.java)
                startActivity(intent)
            }


        }

    }


    private fun paymentClickItem(position: Int) {
        val paymentType = paymentTypeList[position]
        this.startIntent(DetailActivity::class.java, "payment_detail", paymentTypeList[position])

    }

    private fun paymentClickButtonItem(position: Int) {
        val paymentType = paymentTypeList[position]
        this.startIntent(AddPaymentActivity::class.java, "payment_add", paymentTypeList[position])

    }


    private fun initializeView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }


}