package com.talip.lembas.model

import java.io.Serializable

data class Restaurant(var restaurant_id : String?, var restaurant_name : String?, var food_id : List<String>?, var image_url : String?, var min_price : String?, var time : String?, var rating : Float?) :
    Serializable{}
