package com.talip.lembas.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.talip.lembas.data.DataSource
import com.talip.lembas.data.FoodDataSource
import com.talip.lembas.model.MyUser
import com.talip.lembas.repo.AuthRepository
import com.talip.lembas.repo.FirebaseRepository
import com.talip.lembas.repo.FoodRepository
import com.talip.lembas.retrofit.ApiUtils
import com.talip.lembas.retrofit.FoodDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        val fb = Firebase.auth
        return fb

    }


    @Provides
    @Singleton
    fun provideDataSource(fAuth: FirebaseAuth, databaseRef: DatabaseReference): DataSource {
        return DataSource(fAuth, databaseRef)
    }

    @Provides
    @Singleton
    fun provideFoodDataSource(foodDao: FoodDao): FoodDataSource {
        return FoodDataSource(foodDao)
    }

    @Provides
    @Singleton
    fun provideFoodDao(): FoodDao {
        return ApiUtils.getFoodDao()
    }

    @Provides
    @Singleton
    fun provideFoodRepository(foodDataSource: FoodDataSource): FoodRepository {
        return FoodRepository(foodDataSource)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(dataSource: DataSource): AuthRepository {
        return AuthRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideFirebaseRepository(dataSource: DataSource): FirebaseRepository {
        return FirebaseRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideDatabaseReferance(): DatabaseReference {
        val db =
            FirebaseDatabase.getInstance("https://lembas-cbae4-default-rtdb.europe-west1.firebasedatabase.app")

        return db.getReference("lembasDatabase")
    }

    @Provides
    @Singleton fun provideUser(authRepo : AuthRepository) : MyUser?{
        return authRepo.currentUser().value
    }



}