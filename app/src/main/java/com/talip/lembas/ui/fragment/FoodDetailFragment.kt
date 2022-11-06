package com.talip.lembas.ui.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.talip.lembas.R
import com.talip.lembas.databinding.FragmentFoodDetailBinding
import com.talip.lembas.model.Restaurant
import com.talip.lembas.ui.viewmodel.FoodDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodDetailFragment : Fragment() {
    private lateinit var binding: FragmentFoodDetailBinding
    private lateinit var viewModel: FoodDetailViewModel
    var foodNumber = 0
    var foodBagPrice = 0
    val bundle: FoodDetailFragmentArgs by navArgs()
    lateinit var res: Restaurant

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_detail, container, false)
        binding.foodBagPrice = foodBagPrice.toString()
        binding.foodNumber = foodNumber.toString()
        binding.foodDetailFragment = this
        var baseUrl = "http://kasimadalan.pe.hu/yemekler/resimler/"

        val food = bundle.food
        res = bundle.restaurant

        binding.food = food
        Picasso.get().load(baseUrl + (food.yemek_resim_adi)).fit().into(binding.foodDetailImage)

        return binding.root
    }

    fun addBag() {
        val sp = requireContext().getSharedPreferences("res", Context.MODE_PRIVATE)
        val editor = sp.edit()
        val getRes = sp.getString("res", "0")


        if (foodNumber > 0) {
            if (res.restaurant_id == getRes || getRes == "0") {
                viewModel.addBag(
                    bundle.food.yemek_adi,
                    bundle.food.yemek_resim_adi,
                    bundle.food.yemek_fiyat,
                    foodNumber,
                    requireContext()

                )
                editor.putString("res", res.restaurant_id)
                editor.commit()

                back()
            } else {


                var alert = AlertDialog.Builder(requireContext())
                alert.setMessage(R.string.cantOrder)
                alert.setPositiveButton(R.string.ok) { s, d -> }
                alert.show()
                Log.e("resPref", " Gelen Res: $getRes")
            }


        } else {
            val alert = AlertDialog.Builder(requireContext())
            alert.setMessage(R.string.addMore)
            alert.setPositiveButton(R.string.ok) { s, d -> }
            alert.show()
        }

    }

    fun back() {
        activity?.onBackPressed()
    }

    fun foodPlus() {

        foodNumber += 1
        foodBagPrice = binding.food!!.yemek_fiyat * foodNumber
        Log.e("foodDetail", "$foodNumber --- $foodBagPrice")
        binding.foodBagPrice = foodBagPrice.toString()
        binding.foodNumber = foodNumber.toString()

    }

    fun foodMinus() {
        if (foodNumber > 0) {
            foodNumber -= 1
            foodBagPrice = binding.food!!.yemek_fiyat * foodNumber
            Log.e("foodDetail", "$foodNumber --- $foodBagPrice")
            binding.foodBagPrice = foodBagPrice.toString()
            binding.foodNumber = foodNumber.toString()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = resources.getColor(R.color.white)
        requireActivity().window.getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        val tempViewModel: FoodDetailViewModel by viewModels()
        viewModel = tempViewModel

    }
}