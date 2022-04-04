package com.invoice.list.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.invoice.list.R
import com.invoice.list.model.PaymentType

class PaymentTypeOperation(val context: Context) {

    private var paymentTypeListDatabase: SQLiteDatabase? = null
    private val dbOpenHelper: DatabaseOpenHelper =
        DatabaseOpenHelper(context, context.getString(R.string.PAYMENT_TYPE_TABLE_NAME), null, 1)


    private fun open() {

        paymentTypeListDatabase = dbOpenHelper.writableDatabase
    }

    private fun close() {

        if (paymentTypeListDatabase != null && paymentTypeListDatabase!!.isOpen) {
            paymentTypeListDatabase!!.close()
        }
    }


    fun addPaymentType(model: PaymentType): Long {

        val cv = ContentValues()

        cv.put(context.getString(R.string.paymentName), model.name)
        cv.put(context.getString(R.string.paymentPeriod), model.period)
        cv.put(context.getString(R.string.paymentPeriodDay), model.periodDay)

        open()


        val result =
            paymentTypeListDatabase!!.insert(
                context.getString(R.string.PAYMENT_TYPE_TABLE_NAME),
                null,
                cv
            )

        close()

        return result

    }

    fun updatePaymentType(model: PaymentType): Int {

        val cv = ContentValues()

        cv.put(context.getString(R.string.paymentName), model.name)
        cv.put(context.getString(R.string.paymentPeriod), model.period)
        cv.put(context.getString(R.string.paymentPeriodDay), model.periodDay)

        open()

        val result = paymentTypeListDatabase!!.update(
            context.getString(R.string.PAYMENT_TYPE_TABLE_NAME),
            cv,
            "id=${model.id}",
            null
        )

        close()

        return result

    }

    fun deletePaymentType(id: Int): Int {

        open()

        val result = paymentTypeListDatabase!!.delete(
            context.getString(R.string.PAYMENT_TYPE_TABLE_NAME),
            "id=$id",
            null
        )

        close()

        return result
    }

    @SuppressLint("Range")
    fun getPaymentTypeList(): List<PaymentType> {

        val list = ArrayList<PaymentType>()
        var paymentType: PaymentType


        open()

        val c = getCursor()

        if (c.moveToFirst()) {

            do {
                paymentType = PaymentType()
                paymentType.name =
                    c.getString(c.getColumnIndex(context.getString(R.string.paymentName)))
                paymentType.id = c.getInt(c.getColumnIndex("id"))
                paymentType.period =
                    c.getString(c.getColumnIndex(context.getString(R.string.paymentPeriod)))
                paymentType.periodDay =
                    c.getInt(c.getColumnIndex(context.getString(R.string.paymentPeriodDay)))
                list.add(paymentType)
            } while (c.moveToNext())

        }

        close()

        return list
    }

    private fun getCursor(): Cursor {

        val query = "SELECT * FROM ${context.getString(R.string.PAYMENT_TYPE_TABLE_NAME)}"

        return paymentTypeListDatabase!!.rawQuery(query, null)
    }

}