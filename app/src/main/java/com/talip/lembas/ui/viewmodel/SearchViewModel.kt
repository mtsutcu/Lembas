package com.talip.lembas.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.talip.lembas.model.Food
import com.talip.lembas.model.Restaurant
import com.talip.lembas.repo.FirebaseRepository
import com.talip.lembas.repo.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(var fireRepo: FirebaseRepository) : ViewModel() {
    var restaurantList = MutableLiveData<List<Restaurant>>()
    var isLoading = fireRepo.isLoading


    fun searchRestaurant(searchText: String) {
        if (searchText.isNotEmpty() && searchText != "" && searchText != " ") {
            restaurantList.value = fireRepo.searchRestaurant(searchText).value
        } else {
            var emptyList = ArrayList<Restaurant>()
            restaurantList.value = emptyList
        }
        Log.e("searchRess", " Viewmodel : ${searchText}")
    }
}