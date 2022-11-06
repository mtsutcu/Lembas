package com.talip.lembas.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.talip.lembas.MainActivity
import com.talip.lembas.R
import com.talip.lembas.databinding.FragmentSearchBinding
import com.talip.lembas.model.Restaurant
import com.talip.lembas.ui.adapter.RestaurantAdapter
import com.talip.lembas.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var navController =
            (requireActivity() as MainActivity).findNavController(R.id.fragmentContainerViewMain)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        viewModel.restaurantList.observe(viewLifecycleOwner) {
            Log.e("searchRess", "$it")
            val adapter = RestaurantAdapter(requireContext(), it, navController, "Search")
            binding.resAdapter = adapter
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.searchFragRecyclerView.visibility = View.INVISIBLE
                binding.searchProgress.visibility = View.VISIBLE
            } else {
                binding.searchProgress.visibility = View.INVISIBLE
                binding.searchFragRecyclerView.visibility = View.VISIBLE
            }
        }
        binding.editText2.addTextChangedListener {
            viewModel.searchRestaurant(it.toString())
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = resources.getColor(R.color.white)
        requireActivity().window.getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        val tempViewModel: SearchViewModel by viewModels()
        viewModel = tempViewModel
    }

}