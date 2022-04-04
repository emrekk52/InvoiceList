package com.invoice.list.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.invoice.list.R

class DatabaseOpenHelper(
    val context: Context,
    databaseName: String = "",
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, databaseName, factory, version) {


    override fun onCreate(p0: SQLiteDatabase) {

        createPaymentType(p0)
        createPayment(p0)

    }


    private fun createPayment(p0: SQLiteDatabase) {

        val amount = context.getString(R.string.paymentAmount)
        val date = context.getString(R.string.paymentDate)
        val paymentTypeId = context.getString(R.string.paymentTypeId)

        val query =
            "CREATE TABLE ${context.getString(R.string.PAYMENT_TABLE_NAME)}(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, $amount TEXT NOT NULL, $date TEXT NOT NULL,$paymentTypeId INTEGER NOT NULL)"
        p0.execSQL(query)
    }


    private fun createPaymentType(p0: SQLiteDatabase) {

        val name = context.getString(R.string.paymentName)
        val period = context.getString(R.string.paymentPeriod)
        val period_day = context.getString(R.string.paymentPeriodDay)

        val query =
            "CREATE TABLE ${context.getString(R.string.PAYMENT_TYPE_TABLE_NAME)}(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, $name TEXT NOT NULL, $period TEXT NULL,$period_day INTEGER NULL)"
        p0.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {


    }


}