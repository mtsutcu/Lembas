package com.talip.lembas.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import com.talip.lembas.MainActivity
import com.talip.lembas.R
import com.talip.lembas.databinding.FragmentHomeBinding
import com.talip.lembas.ui.adapter.CampaignAdapter
import com.talip.lembas.ui.adapter.RestaurantAdapter
import com.talip.lembas.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
     private lateinit var viewModel: HomeViewModel
     private lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container, false)

        binding.homeFragment = this

        viewModel.isLoading.observe(viewLifecycleOwner){
            if(it){
                binding.homeProgress.visibility = View.VISIBLE
                binding.textViewToolbar.visibility = View.GONE
                binding.homeConst.visibility = View.GONE
            } else{
                binding.homeProgress.visibility = View.GONE
                binding.textViewToolbar.visibility = View.VISIBLE
                binding.homeConst.visibility = View.VISIBLE

            }
        }

        viewModel.campaignList.observe(viewLifecycleOwner){
            Log.e("camp","Home Campaigns: $it")
            if (it != null){
                val adapter = CampaignAdapter(requireContext(),it)
                binding.campaignAdapter = adapter
            }

        }

        var navController = (requireActivity() as MainActivity).findNavController(R.id.fragmentContainerViewMain)
        viewModel.restaurantList.observe(this as LifecycleOwner){
            Log.e("res","Home Fragment Restaurants: ${it.size}")
            if (it != null){
                Log.e("res","Home Fragment Restaurants Adapter çalıştı")
                val adapter = RestaurantAdapter(requireContext(),it,navController,"Home")
                binding.resAdapter = adapter
            }

        }
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = resources.getColor(R.color.myGreen)
        val tempViewModel : HomeViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.statusBarColor = resources.getColor(R.color.myGreen)

    }

}