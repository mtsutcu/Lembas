package com.talip.lembas.ui.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.talip.lembas.model.MyUser
import com.talip.lembas.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(var authRepo: AuthRepository) : ViewModel() {
    var user = MutableLiveData<MyUser>()
    var isLoading = authRepo.isLoading

    fun currentUser() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(coroutineContext) {
                user.value = authRepo.currentUser().value
                Log.e("viewc", " Login View Model user :${user.value}")
            }
        }
    }

    fun createUserWithEmail(
        email: String,
        password: String,
        address: String,
        context: Context,
        view: View
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(coroutineContext) {
                authRepo.createUserWithEmail(email, password, address, context, view)
                user.value = authRepo.currentUser().value
            }
        }
    }
}

