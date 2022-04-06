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
        val res = operation.addPayment(model)
        getPaymentList()
    }


    fun deletePaymentList(id: Int) {
        val res = operation.deletePayment(id)
        getPaymentList()
    }

}

//PaymentViewmodel sınıfında context ve ödeme id'si kullanmak üzere parametre yollayabilmemize olanak sağlar
class PaymentViewModelFactory(private val application: Application, private val param: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PaymentViewModel(application, param) as T
    }
}