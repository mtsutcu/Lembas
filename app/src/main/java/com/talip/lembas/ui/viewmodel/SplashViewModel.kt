package com.talip.lembas.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.talip.lembas.model.MyUser
import com.talip.lembas.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(var authRepo: AuthRepository) : ViewModel() {

    var user = MutableLiveData<MyUser>()

    init {
        currentUser()
        Log.e("viewc", "${user.value} init çalıştı")
    }


    fun currentUser() {
        user.value = authRepo.currentUser().value
        Log.e("viewc", " Login View Model user :${user.value}")
    }

    fun exit() {
        authRepo.exit()
        Log.e("viewc", " Main View Model exit :${user.value}")
        user.value = MyUser(null, null)
    }
}