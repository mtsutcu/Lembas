package com.talip.lembas.repo

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.talip.lembas.data.DataSource
import com.talip.lembas.model.MyUser

class AuthRepository(var dataSource: DataSource) {
    var isLoading = dataSource.isLoading

        suspend fun createUserWithEmail(
        email: String,
        password: String,
        address: String,
        context: Context,
        view: View
    ) =
        dataSource.createUserWithEmail(email, password, address, context, view)

    fun currentUser(): MutableLiveData<MyUser> = dataSource.currentUser()

    fun exit() = dataSource.exit()

    suspend fun login(email: String, password: String, context: Context): MutableLiveData<MyUser> =
        dataSource.login(email, password, context)

    fun sendPasswordLink(email: String, context: Context, view: View) =
        dataSource.sendPasswordLink(email, context, view)

    fun updateAddress(address : String){
        dataSource.updateAddress(address)
    }

    fun getAddress() : MutableLiveData<String> = dataSource.getAddress()
}