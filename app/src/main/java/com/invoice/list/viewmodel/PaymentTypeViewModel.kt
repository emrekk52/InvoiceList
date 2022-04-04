package com.invoice.list.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.invoice.list.database.PaymentOperation
import com.invoice.list.database.PaymentTypeOperation
import com.invoice.list.model.PaymentType

class PaymentTypeViewModel(application: Application) : AndroidViewModel(application) {

    private val operation: PaymentTypeOperation
    private val paymentOperation: PaymentOperation
    val paymentTypeList = MutableLiveData<List<PaymentType>>()

    init {
        operation = PaymentTypeOperation(application)
        paymentOperation = PaymentOperation(application)
        getPaymentTypeList()
    }

    private fun getPaymentTypeList() {
        paymentTypeList.value = operation.getPaymentTypeList()

    }


    fun addPaymentTypeList(model: PaymentType) {
        val res = operation.addPaymentType(model)
        getPaymentTypeList()
    }

    fun updatePaymentTypeList(model: PaymentType) {
        val res = operation.updatePaymentType(model)
        getPaymentTypeList()
    }

    fun deletePaymentTypeList(id: Int) {
        val res = operation.deletePaymentType(id)
        //ödeme tipine ait ödemelerin silinmesi
        val res2 = paymentOperation.deleteTypePayment(id)
        getPaymentTypeList()
    }

}