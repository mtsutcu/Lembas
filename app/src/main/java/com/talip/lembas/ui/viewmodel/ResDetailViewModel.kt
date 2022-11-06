package com.talip.lembas.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.talip.lembas.model.Food
import com.talip.lembas.repo.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class ResDetailViewModel @Inject constructor(var foodRepo: FoodRepository) : ViewModel() {
    var foodList = MutableLiveData<List<Food>>()

    init {
        getFoods()
    }

    fun getFoods() {
        CoroutineScope(Dispatchers.Main).launch {
            foodList.value = foodRepo.getFoods()
        }
    }

    fun searchFood(word: String?) {
        if (word != null && word != "") {
            CoroutineScope(Dispatchers.Main).launch {
                foodList.value = foodRepo.searchFoods(word)
            }
        } else {
            getFoods()
        }
    }
}
