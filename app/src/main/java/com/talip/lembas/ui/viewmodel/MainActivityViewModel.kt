package com.talip.lembas.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.talip.lembas.model.Bag
import com.talip.lembas.model.MyUser
import com.talip.lembas.model.Order
import com.talip.lembas.repo.AuthRepository
import com.talip.lembas.repo.FirebaseRepository
import com.talip.lembas.repo.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    var authRepo: AuthRepository,
    var foodRepo: FoodRepository,
    var fireRepo: FirebaseRepository
) : ViewModel() {

    var user = MutableLiveData<MyUser>()
    var bagFoodList = MutableLiveData<List<Bag>>()
    var order = MutableLiveData<Order>()

    init {
        currentUser()
        Log.e("viewc", "${user.value} init çalıştı")
        getBagFoods()
        getOrder()
    }

    fun getBagFoods() {
        CoroutineScope(Dispatchers.Main).launch {
            bagFoodList.value = foodRepo.getBagFoods(user.value!!.email.toString())
        }
    }

    fun currentUser() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(coroutineContext) {
                user.value = authRepo.currentUser().value
                Log.e("viewc", " Login View Model user :${user.value}")
            }
        }
    }

    fun getOrder() {
        order = fireRepo.getOrder()
        Log.e("mainOrder", "Main viewmodel Order : ${order.value}")
    }

    fun getNot() = fireRepo.getNot()

    fun exit() {
        authRepo.exit()
        Log.e("viewc", " Main View Model exit :${user.value}")
        user.value = MyUser(null, null)
    }
}