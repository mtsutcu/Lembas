package com.talip.lembas.repo

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.talip.lembas.data.DataSource
import com.talip.lembas.model.Order
import com.talip.lembas.model.Restaurant

class FirebaseRepository(var dataSource: DataSource) {
    var isLoading = dataSource.isLoading

    fun getCampaigns(): MutableLiveData<List<String>> = dataSource.getCampaigns()
    fun getRestaurants(): MutableLiveData<List<Restaurant>> = dataSource.getRestaurants()
    fun searchRestaurant(searchText: String): MutableLiveData<List<Restaurant>> =
        dataSource.searchRestaurant(searchText)

    suspend fun createOrder(order: Order, context: Context, view: View) =
        dataSource.createOrder(order, context, view)

    fun getOrder() : MutableLiveData<Order>  {
        Log.e("Orderr","Order repo : ${dataSource.getOrder().value}")

        return dataSource.getOrder()
    }

    fun deleteOrder() = dataSource.deleteOrder()
    fun getNot()= dataSource.getNot()
}