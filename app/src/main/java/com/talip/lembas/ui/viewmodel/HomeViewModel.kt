package com.talip.lembas.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.talip.lembas.model.Restaurant
import com.talip.lembas.repo.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(var firebaseRepository: FirebaseRepository) : ViewModel() {
    var campaignList = MutableLiveData<List<String>>()
    var restaurantList = MutableLiveData<List<Restaurant>>()
    var isLoading = firebaseRepository.isLoading


    init {
        getCampaigns()
        getRestaurants()
        Log.e("camp", "Camp List Home view model init: ${campaignList.value}")
    }

    fun getCampaigns() {
        campaignList = firebaseRepository.getCampaigns()
        Log.e("viewc", " Camp list Home view model:${campaignList.value}")

    }

    fun getRestaurants() {
        restaurantList = firebaseRepository.getRestaurants()
        Log.e("res", " Restaurant list Home view model:${restaurantList.value}")
    }
}