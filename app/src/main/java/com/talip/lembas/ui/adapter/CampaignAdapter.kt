package com.talip.lembas.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.talip.lembas.databinding.CampaignItemDesignBinding

class CampaignAdapter(var mContext: Context, var campaignList:List<String>)
    : RecyclerView.Adapter<CampaignAdapter.CampCardBindingHolder>() {

    inner class CampCardBindingHolder(binding:CampaignItemDesignBinding) : RecyclerView.ViewHolder(binding.root){
        var binding:CampaignItemDesignBinding
        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampCardBindingHolder {
        val binding = CampaignItemDesignBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CampCardBindingHolder(binding)
    }

    override fun onBindViewHolder(holder: CampCardBindingHolder, position: Int) {
        val campaign = campaignList.get(position)
        val t = holder.binding
            Picasso.get().load(campaign).fit().into(t.imageView)
    }

    override fun getItemCount(): Int {
        return campaignList.size
    }
}