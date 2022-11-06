package com.talip.lembas.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.talip.lembas.MainActivity
import com.talip.lembas.R
import com.talip.lembas.databinding.FragmentBagBinding
import com.talip.lembas.ui.adapter.BagAdapter
import com.talip.lembas.ui.viewmodel.BagViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BagFragment : Fragment() {
    private lateinit var binding: FragmentBagBinding
    private lateinit var viewModel: BagViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bag, container, false)
        binding.bagFragment = this



        viewModel.bagFoodList.observe(viewLifecycleOwner) {
            var totalPrice = 0

            if (it.isNotEmpty()) {
                binding.textView11.visibility = View.GONE
                binding.bagBottomToolbar.visibility = View.VISIBLE
                binding.bagTotalPrice.visibility = View.VISIBLE
                binding.orderButton.visibility = View.VISIBLE


                for (i in it) {
                    totalPrice += (i.yemek_siparis_adet * i.yemek_fiyat)


                }
                binding.totalPrice = totalPrice.toString()

                val adapter = BagAdapter(requireContext(), it, viewModel)
                binding.bagAdapter = adapter


            } else {
                (requireActivity() as MainActivity).getBagFoods()
                binding.bagRecyclerView.visibility = View.INVISIBLE
                binding.textView11.visibility = View.VISIBLE
                binding.bagBottomToolbar.visibility = View.GONE
                binding.bagTotalPrice.visibility = View.INVISIBLE
                binding.orderButton.visibility = View.INVISIBLE
            }


            Log.e("bagFood", "$it")
        }

        return binding.root
    }

    fun createOrder(view: View) {
        viewModel.createOrder(requireContext(), view)
        val sp = requireContext().getSharedPreferences("res", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.remove("res")
        editor.commit()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = resources.getColor(R.color.myGreen)
        val tempViewModel: BagViewModel by viewModels()
        viewModel = tempViewModel

    }


}