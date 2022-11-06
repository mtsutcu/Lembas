package com.talip.lembas.model

import com.google.firebase.Timestamp
import java.io.Serializable

data class Order(var order_id : String?,var bagList : List<Bag>?, var orderState : Int? ) :
    Serializable{}
