package com.talip.lembas.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.talip.lembas.model.Bag
import com.talip.lembas.model.CRUDCevap
import com.talip.lembas.model.Food
import com.talip.lembas.retrofit.FoodDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodDataSource(var foodDao: FoodDao) {
    suspend fun getFoods(): List<Food> =
        withContext(Dispatchers.IO) {
           foodDao.getFoods().yemekler as ArrayList<Food>
        }


    suspend fun searchFood(word: String): List<Food> {
        var updateList = ArrayList<Food>()
        withContext(Dispatchers.IO) {
            foodDao.getFoods().yemekler


            for (i in foodDao.getFoods().yemekler!!) {
                if ((i.yemek_adi.lowercase().contains(word.lowercase()))) {
                    Log.e("contains", "${i.yemek_adi}")
                    updateList.add(i)
                    Log.e("contains", " Liste: $updateList")

                }
            }

        }
        return updateList
    }

    suspend fun addBag(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kullanici_adi: String
    ) {
        foodDao.addBag(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi)

    }

    suspend fun getBagFoods(kullanici_adi: String): List<Bag> =
        withContext(Dispatchers.IO) {
            try {
                foodDao.getBagFoods(kullanici_adi).sepet_yemekler as ArrayList<Bag>
            } catch (e: Exception) {
                ArrayList<Bag>()
            }
        }


    suspend fun deleteBagFood(sepet_yemek_id: Int, kullanici_adi: String) {

        foodDao.deleteBagFood(sepet_yemek_id, kullanici_adi)
    }


}