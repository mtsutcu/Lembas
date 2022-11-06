package com.talip.lembas.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.talip.lembas.databinding.RestaurantCardDesignBinding
import com.talip.lembas.model.Restaurant
import com.talip.lembas.ui.fragment.HomeFragmentDirections
import com.talip.lembas.ui.fragment.SearchFragmentDirections


class RestaurantAdapter(
    var mContext: Context,
    var resList: List<Restaurant>,
    var navController: NavController,
    var fragment: String
) : RecyclerView.Adapter<RestaurantAdapter.ResCardBindingHolder>() {

    inner class ResCardBindingHolder(binding: RestaurantCardDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: RestaurantCardDesignBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResCardBindingHolder {
        val binding =
            RestaurantCardDesignBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ResCardBindingHolder(binding)
    }

    override fun onBindViewHolder(holder: ResCardBindingHolder, position: Int) {
        val res = resList.get(position)
        val t = holder.binding

        t.resAdapter = this
        t.restaurantObject = res

        t.resCardView.setOnClickListener {
            if (fragment == "Home") {
                val trans = HomeFragmentDirections.Companion.toResDetail(restaurant = res)
                navController.navigate(trans)
            } else {
                val trans = SearchFragmentDirections.Companion.toResDetail2(restaurant = res)
                navController.navigate(trans)
            }
        }
        Picasso.get().load(res.image_url).fit().into(t.imageViewRes)
    }

    override fun getItemCount(): Int {
        return resList.size
    }
}