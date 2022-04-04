package com.invoice.list.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.invoice.list.database.PaymentOperation
import com.invoice.list.model.Payment


class PaymentViewModel(application: Application, val paymentId: Int) :ViewModel() {

    private val operation: PaymentOperation
    val paymentList = MutableLiveData<List<Payment>>()

    init {
        operation = PaymentOperation(application)
        getPaymentList()
    }

    private fun getPaymentList() {
        paymentList.value = operation.getPaymentList(paymentId)
    }


    fun addPaymentList(model: Payment) {
        println(model.paymentTypeId)
        val res = operation.addPayment(model)
        getPaymentList()
    }


    fun deletePaymentList(id: Int) {
        val res = operation.deletePayment(id)
        getPaymentList()
    }

}

class PaymentViewModelFactory(private val mApplication: Application, private val mParam: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PaymentViewModel(mApplication, mParam) as T
    }
}