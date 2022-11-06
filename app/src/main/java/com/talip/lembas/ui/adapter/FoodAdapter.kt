package com.talip.lembas.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.talip.lembas.databinding.FoodCardDesignBinding
import com.talip.lembas.model.Food
import com.talip.lembas.model.Restaurant
import com.talip.lembas.ui.fragment.ResDetailFragmentDirections

class FoodAdapter(var mContext: Context, var foodList: List<Food>, var res: Restaurant) :
    RecyclerView.Adapter<FoodAdapter.FoodCardBindingHolder>() {

    inner class FoodCardBindingHolder(binding: FoodCardDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: FoodCardDesignBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCardBindingHolder {
        val binding = FoodCardDesignBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return FoodCardBindingHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodCardBindingHolder, position: Int) {
        var baseUrl = "http://kasimadalan.pe.hu/yemekler/resimler/"
        val food = foodList.get(position)
        val t = holder.binding

        Picasso.get().load(baseUrl + (food.yemek_resim_adi)).fit().into(t.imageViewFoodCard)
        t.foodName = food.yemek_adi
        t.foodPrice = food.yemek_fiyat.toString()

        t.foodCardView.setOnClickListener {
            val trans = ResDetailFragmentDirections.toFoodDetail(food, res)
            Navigation.findNavController(t.foodCardView).navigate(trans)
        }
    }


    override fun getItemCount(): Int {
        return foodList.size
    }
}