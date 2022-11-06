package com.talip.lembas.retrofit

class ApiUtils {
    companion object{
        var BASER_URL = "http://kasimadalan.pe.hu/"
        fun getFoodDao():FoodDao{
            return RetrofitClient.getClient(BASER_URL).create(FoodDao::class.java)
        }
    }
}