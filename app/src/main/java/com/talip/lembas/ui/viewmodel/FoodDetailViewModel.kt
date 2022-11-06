package com.talip.lembas.ui.viewmodel

import android.content.Context
import android.content.ContextWrapper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.talip.lembas.MainActivity
import com.talip.lembas.model.Bag
import com.talip.lembas.model.CRUDCevap
import com.talip.lembas.repo.AuthRepository
import com.talip.lembas.repo.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    var foodRepo: FoodRepository,
    var authRepo: AuthRepository

) : ViewModel() {
    var user = authRepo.currentUser().value
    var bagFoodList = MutableLiveData<List<Bag>>()

    init {
        getBagFoods()
    }

    fun getBagFoods() {
        CoroutineScope(Dispatchers.Main).launch {
            bagFoodList.value = foodRepo.getBagFoods(user?.email.toString())
        }
    }

    fun addBag(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        context: Context
    ) {
        var bagPiece = 0
        var bagID = -1
        for (i in bagFoodList.value!!) {
            if (i.yemek_adi == yemek_adi) {
                bagPiece = i.yemek_siparis_adet
                bagID = i.sepet_yemek_id
            }
        }
        if (bagPiece == 0) {
            CoroutineScope(Dispatchers.Main).launch {
                foodRepo.addBag(
                    yemek_adi,
                    yemek_resim_adi,
                    yemek_fiyat,
                    yemek_siparis_adet,
                    user!!.email.toString()
                )
                ((context as ContextWrapper).baseContext as MainActivity).getBagFoods()
                getBagFoods()

            }
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                foodRepo.deleteBagFood(bagID, user!!.email.toString())
                foodRepo.addBag(
                    yemek_adi,
                    yemek_resim_adi,
                    yemek_fiyat,
                    (bagPiece + yemek_siparis_adet),
                    user!!.email.toString()
                )
                getBagFoods()
            }
        }
    }
}