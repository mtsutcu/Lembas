package com.talip.lembas.ui.viewmodel

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.talip.lembas.model.Bag
import com.talip.lembas.model.Order
import com.talip.lembas.repo.AuthRepository
import com.talip.lembas.repo.FirebaseRepository
import com.talip.lembas.repo.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BagViewModel @Inject constructor(
    var foodRepo: FoodRepository,
    var authRepo: AuthRepository,
    var fireRepo: FirebaseRepository
) :
    ViewModel() {
    var bagFoodList = MutableLiveData<List<Bag>>()
    var user = authRepo.currentUser().value

    init {
        getBagFoods()
    }

    fun getBagFoods() {
        CoroutineScope(Dispatchers.Main).launch {
            bagFoodList.value = foodRepo.getBagFoods(user?.email.toString())
        }
    }

    fun deleteBagFood(sepet_yemek_id: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            foodRepo.deleteBagFood(sepet_yemek_id, user!!.email.toString())
            getBagFoods()
        }
    }

    fun createOrder(context: Context, view: View) {
        val order = Order(user!!.user_id, bagFoodList.value, 0)
        CoroutineScope(Dispatchers.Main).launch {
            fireRepo.createOrder(order, context, view)
            bagFoodList.value?.forEach {
                deleteBagFood(it.sepet_yemek_id)
            }
        }
    }

}