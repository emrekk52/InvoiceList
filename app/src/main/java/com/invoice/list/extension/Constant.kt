package com.invoice.list.extension

import com.invoice.list.model.Period

class Constant {

    companion object {

        const val WEEK_DAY = 7
        const val MONTH_DAY = 31
        const val YEAR_DAY = 365

        val periodList = listOf(Period.HAFTALIK, Period.AYLIK, Period.YILLIK)

    }
}

