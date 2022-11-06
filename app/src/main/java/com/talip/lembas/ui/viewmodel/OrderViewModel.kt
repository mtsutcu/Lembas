package com.talip.lembas.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.talip.lembas.model.Order
import com.talip.lembas.repo.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class OrderViewModel @Inject constructor(var fireRepo: FirebaseRepository) : ViewModel() {
    var order = MutableLiveData<Order>()

    init {
        getOrder()
    }

    fun getOrder() {
        Log.e("Orderr", "Order Viewmodel : ${fireRepo.getOrder().value}")
        order = fireRepo.getOrder()
    }

    fun deleteOrder() = fireRepo.deleteOrder()
}