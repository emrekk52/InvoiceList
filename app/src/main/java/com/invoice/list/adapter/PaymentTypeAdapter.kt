package com.invoice.list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.invoice.list.R
import com.invoice.list.model.PaymentType

class PaymentTypeAdapter(
    val list: List<PaymentType>,
    val itemClick: (position: Int) -> Unit,
    val addPaymentClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<PaymentTypeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentTypeViewHolder {
        return PaymentTypeViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.payment_type_list_design, parent, false),
            itemClick,
            addPaymentClick
        )
    }

    override fun onBindViewHolder(holder: PaymentTypeViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}

class PaymentTypeViewHolder(
    itemView: View,
    var itemClick: (position: Int) -> Unit,
    var addPaymentClick: (position: Int) -> Unit
) :
    RecyclerView.ViewHolder(itemView) {


    val paymentName: TextView
    val paymentPeriod: TextView
    val paymentPeriodDay: TextView


    init {
        paymentName = itemView.findViewById(R.id.paymentName)
        paymentPeriod = itemView.findViewById(R.id.paymentPeriod)
        paymentPeriodDay = itemView.findViewById(R.id.paymentPeriodDay)
        itemView.findViewById<Button>(R.id.btnAddPayment)
            .setOnClickListener { addPaymentClick(adapterPosition) }
        itemView.setOnClickListener { itemClick(adapterPosition) }
    }

    @SuppressLint("SetTextI18n")
    fun bind(model: PaymentType) {

        paymentName.text = "Ödeme başlığı: ${model.name}"
        if (model.period?.isNotEmpty()==true)
            paymentPeriod.text = "Ödeme periyodu: ${model.period}".also { paymentPeriod.visibility = View.VISIBLE } else paymentPeriod.visibility = View.GONE

        if (model.periodDay != 0)
            paymentPeriodDay.text =
                "Ödeme günü: ${model.periodDay}".also { paymentPeriodDay.visibility = View.VISIBLE } else paymentPeriodDay.visibility = View.GONE
    }

}