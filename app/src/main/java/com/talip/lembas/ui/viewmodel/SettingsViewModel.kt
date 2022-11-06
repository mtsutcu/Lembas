package com.talip.lembas.ui.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.talip.lembas.model.MyUser
import com.talip.lembas.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(var authRepo: AuthRepository) : ViewModel() {
    var user = MutableLiveData<MyUser>()
    var address = MutableLiveData<String>()

    init {
        getUser()
        getAdress()
    }


    fun getUser() {
        user = authRepo.currentUser()
    }

    fun exit() {
        authRepo.exit()
    }

    fun sendPasswordLink(context: Context, view: View) =
        authRepo.sendPasswordLink(user.value!!.email.toString(), context, view)


    fun updateAddress(address: String) {
        authRepo.updateAddress(address)
    }

    fun getAdress() {
        address = authRepo.getAddress()
        Log.e("addr", " Address View model: ${address.value}")

    }
}
