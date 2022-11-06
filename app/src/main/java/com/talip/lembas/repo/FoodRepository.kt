package com.talip.lembas.repo

import com.talip.lembas.data.FoodDataSource
import com.talip.lembas.model.Bag
import com.talip.lembas.model.Food


class FoodRepository(var foodDataSource: FoodDataSource) {

    suspend fun getFoods(): List<Food> = foodDataSource.getFoods()
    suspend fun searchFoods(word: String): List<Food> = foodDataSource.searchFood(word)
    suspend fun addBag(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kullanici_adi: String,
    ) = foodDataSource.addBag(
        yemek_adi,
        yemek_resim_adi,
        yemek_fiyat,
        yemek_siparis_adet,
        kullanici_adi
    )

    suspend fun getBagFoods(kullanici_adi: String): List<Bag> =
        foodDataSource.getBagFoods(kullanici_adi)

    suspend fun deleteBagFood(sepet_yemek_id : Int,kullanici_adi: String) = foodDataSource.deleteBagFood(sepet_yemek_id,kullanici_adi)



}