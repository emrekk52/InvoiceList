package com.invoice.list.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.invoice.list.R
import com.invoice.list.model.Payment

class PaymentOperation(val context: Context) {

    private var paymentDatabase: SQLiteDatabase? = null
    private val dbOpenHelper: DatabaseOpenHelper =
        DatabaseOpenHelper(context, context.getString(R.string.PAYMENT_TABLE_NAME), null, 1)


    private fun open() {

        paymentDatabase = dbOpenHelper.writableDatabase
    }

    private fun close() {

        if (paymentDatabase != null && paymentDatabase!!.isOpen) {
            paymentDatabase!!.close()
        }
    }


    fun addPayment(model: Payment): Long {

        val cv = ContentValues()

        cv.put(context.getString(R.string.paymentAmount), model.amount)
        cv.put(context.getString(R.string.paymentDate), model.date)
        cv.put(context.getString(R.string.paymentTypeId), model.paymentTypeId)

        open()


        val result =
            paymentDatabase!!.insert(
                context.getString(R.string.PAYMENT_TABLE_NAME),
                null,
                cv
            )

        close()

        return result

    }

    /* fun updatePayment(model: Payment): Int {

        val cv = ContentValues()

        cv.put(context.getString(R.string.paymentAmount), model.amount)
        cv.put(context.getString(R.string.paymentDate), model.date)

        open()


        val result = paymentDatabase!!.update(
            context.getString(R.string.PAYMENT_TABLE_NAME),
            cv,
            "id=${model.id}",
            null
        )

        close()

        return result

    } */


    // istenilen ödemeyi siler
    fun deletePayment(id: Int): Int {

        open()

        val result = paymentDatabase!!.delete(
            context.getString(R.string.PAYMENT_TABLE_NAME),
            "id=$id",
            null
        )

        close()

        return result
    }

    // bağlı olduğu ödeme tipinde ki tüm ödemeleri siler
    fun deleteTypePayment(paymentTypeId: Int): Int {

        open()

        val result = paymentDatabase!!.delete(
            context.getString(R.string.PAYMENT_TABLE_NAME),
            "${context.getString(R.string.paymentTypeId)}=$paymentTypeId",
            null
        )

        close()

        return result
    }

    @SuppressLint("Range")
    fun getPaymentList(paymentId: Int): List<Payment> {

        val list = ArrayList<Payment>()
        var payment: Payment


        open()

        val c = getCursor(paymentId)

        if (c.moveToFirst()) {

            do {
                payment = Payment()
                payment.id = c.getInt(c.getColumnIndex("id"))
                payment.amount =
                    c.getDouble(c.getColumnIndex(context.getString(R.string.paymentAmount)))
                payment.date =
                    c.getString(c.getColumnIndex(context.getString(R.string.paymentDate)))
                payment.paymentTypeId=c.getInt(c.getColumnIndex(context.getString(R.string.paymentTypeId)))
                list.add(payment)
            } while (c.moveToNext())

        }

        close()

        return list
    }

    private fun getCursor(paymentId: Int): Cursor {

        val query = "SELECT * FROM ${context.getString(R.string.PAYMENT_TABLE_NAME)} WHERE ${
            context.getString(R.string.paymentTypeId)
        }=$paymentId"

        return paymentDatabase!!.rawQuery(query, null)
    }

}