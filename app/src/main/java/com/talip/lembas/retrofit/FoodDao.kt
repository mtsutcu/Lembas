package com.talip.lembas.retrofit

import com.talip.lembas.model.BagResponse
import com.talip.lembas.model.CRUDCevap
import com.talip.lembas.model.FoodResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodDao {
    // "http://http://kasimadalan.pe.hu/"


    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun getFoods(): FoodResponse

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun addBag(
        @Field("yemek_adi") yemek_adi: String,
        @Field("yemek_resim_adi") yemek_resim_adi: String,
        @Field("yemek_fiyat") yemek_fiyat: Int,
        @Field("yemek_siparis_adet") yemek_siparis_adet: Int,
        @Field("kullanici_adi") kullanici_adi: String
    ) :CRUDCevap

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun getBagFoods(@Field("kullanici_adi") kullanici_adi: String) : BagResponse

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun deleteBagFood(@Field("sepet_yemek_id") sepet_yemek_id: Int,@Field("kullanici_adi") kullanici_adi: String) : CRUDCevap
}