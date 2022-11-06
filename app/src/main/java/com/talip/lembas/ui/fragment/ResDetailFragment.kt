package com.talip.lembas.ui.fragment


import android.os.Bundle

import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.PopupMenu

import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import com.talip.lembas.R
import com.talip.lembas.databinding.FragmentResDetailBinding
import com.talip.lembas.model.Food
import com.talip.lembas.model.Restaurant
import com.talip.lembas.ui.adapter.FoodAdapter
import com.talip.lembas.ui.viewmodel.ResDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResDetailFragment() : Fragment() {
    private lateinit var binding: FragmentResDetailBinding
    private lateinit var viewModel: ResDetailViewModel
    private var foodList = ArrayList<Food>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_res_detail, container, false)
        val bundle: ResDetailFragmentArgs by navArgs()

        val res = bundle.restaurant
        Log.e("namee", "$res")

        binding.detailFragment = this

        binding.restaurantObject = res

        Picasso.get().load(res.image_url).centerCrop().resize(400, 400).into(binding.detailResImage)

        viewModel.foodList.observe(viewLifecycleOwner) {
            val resFoodList = mutableListOf<Food>()
            for (i in it) {
                if ((res.food_id!!.contains(i.yemek_id.toString()))) {
                    resFoodList.add(i)

                }

            }
            Log.e("foodList", "$resFoodList")
            if (resFoodList.size < res.food_id!!.size) {
                binding.resDetailResCard.visibility = View.GONE
            } else {
                binding.resDetailResCard.visibility = View.VISIBLE
            }



            foodList = resFoodList as ArrayList<Food>
            Log.e("foodList","$foodList")

            val adapter = FoodAdapter(requireContext(), foodList, res)
            binding.foodAdapter = adapter
            Log.e("FOODS", "$it")
        }

        binding.editText.addTextChangedListener {
            Log.e("TextChh", "$it")

            viewModel.searchFood(it.toString())
        }


      binding.sortFloatButton.setOnClickListener {
          val popUp = PopupMenu(requireContext(),it)
          popUp.menuInflater.inflate(R.menu.sort_pop_up_menu,popUp.menu)
          popUp.show()

          popUp.setOnMenuItemClickListener {
              when(it.itemId){
                  R.id.popMaxPrice ->{
                      createAdapter(res,"1")
                      true
                  }
                  R.id.popMinPrice->{
                      createAdapter(res,"0")
                      true
                  }
                  R.id.popAtoZ->{
                      createAdapter(res,"a")
                      true
                  }
                  R.id.popZtoA->{
                      createAdapter(res,"z")
                      true
                  }
                  R.id.popClear->{
                       createAdapter(res,"n")
                      true
                  }
                  else -> false
              }
          }

      }
        return binding.root
    }

    fun createAdapter(res: Restaurant, sort: String) {


            when (sort) {
                "a" -> foodList.sortBy { it.yemek_adi }
                "z" -> foodList.sortByDescending { it.yemek_adi }
                "0" -> foodList.sortBy { it.yemek_fiyat }
                "1" -> foodList.sortByDescending { it.yemek_fiyat }
                "n"->foodList.sortBy { it.yemek_adi }

            }
            val adapter = FoodAdapter(requireContext(), foodList, res)
            binding.foodAdapter = adapter


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = resources.getColor(R.color.myGreen)
        val tempViewModel: ResDetailViewModel by viewModels()
        viewModel = tempViewModel


    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.statusBarColor = resources.getColor(R.color.myGreen)
    }

    fun bac() {
        activity?.onBackPressed()
    }


}