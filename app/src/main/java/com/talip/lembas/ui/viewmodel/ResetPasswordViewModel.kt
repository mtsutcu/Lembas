package com.talip.lembas.ui.viewmodel

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import com.talip.lembas.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(var authRepo: AuthRepository) : ViewModel() {

    fun sendPasswordLink(email: String, context: Context, view: View) {
        authRepo.sendPasswordLink(email, context, view)
    }
}