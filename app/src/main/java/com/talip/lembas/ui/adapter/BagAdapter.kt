package com.talip.lembas.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.talip.lembas.R
import com.talip.lembas.databinding.BagCardDesignBinding
import com.talip.lembas.model.Bag
import com.talip.lembas.ui.viewmodel.BagViewModel

class BagAdapter(var mContext: Context, var bagList: List<Bag>, var viewModel: BagViewModel) :
    RecyclerView.Adapter<BagAdapter.BagCardBindingHolder>() {

    inner class BagCardBindingHolder(binding: BagCardDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: BagCardDesignBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BagCardBindingHolder {
        val binding = BagCardDesignBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return BagCardBindingHolder(binding)
    }

    override fun onBindViewHolder(holder: BagCardBindingHolder, position: Int) {
        var baseUrl = "http://kasimadalan.pe.hu/yemekler/resimler/"
        val bag = bagList.get(position)
        val t = holder.binding

        t.bagFoodName = bag.yemek_adi
        t.bagFoodPiece = (bag.yemek_siparis_adet).toString()
        t.bagFoodPrice = (bag.yemek_fiyat * bag.yemek_siparis_adet).toString()


        t.bagCardView.setOnLongClickListener {
            val sp = mContext.getSharedPreferences("res", Context.MODE_PRIVATE)
            val editor = sp.edit()
            Log.e("resPref", " Silinen Res: ${sp.all}")


            val alert = AlertDialog.Builder(mContext)
            alert.setTitle(R.string.warning)
            alert.setMessage(R.string.foodDel)
            alert.setPositiveButton(R.string.yes) { s, d ->
                deleteBagFood(bag.sepet_yemek_id)
                editor.remove("res")
                editor.commit()
            }
            alert.setNegativeButton(R.string.no) { s, d -> }


            alert.show()

            true
        }

        Picasso.get().load(baseUrl + (bag.yemek_resim_adi)).fit().into(t.imageViewBagFood)
    }

    fun deleteBagFood(bagFoodID: Int) {
        viewModel.deleteBagFood(bagFoodID)
    }

    override fun getItemCount(): Int {
        return bagList.size
    }
}