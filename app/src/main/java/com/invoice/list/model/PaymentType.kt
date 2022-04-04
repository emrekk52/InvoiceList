package com.invoice.list.model

import java.io.Serializable
import kotlin.properties.Delegates

class PaymentType : Serializable {

    lateinit var name: String
    var id: Int? = null
    var period: String? = null
    var periodDay: Int? = null

}