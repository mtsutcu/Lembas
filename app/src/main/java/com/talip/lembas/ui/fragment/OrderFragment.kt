package com.talip.lembas.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.talip.lembas.R
import com.talip.lembas.databinding.FragmentOrderBinding
import com.talip.lembas.ui.viewmodel.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment() {
    private lateinit var binding: FragmentOrderBinding
    private lateinit var viewModel: OrderViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)


        binding.orderFragment = this
        viewModel.order.observe(viewLifecycleOwner) {
            binding.orderState = it.orderState.toString()
            if (it.order_id != null) {
                binding.textViewOrderEmpty.visibility = View.GONE

                binding.lottie.visibility = View.VISIBLE
                binding.seekBar.visibility = View.VISIBLE
                binding.textView14.visibility = View.VISIBLE
                when (it.orderState) {

                    0 -> {
                        binding.lottie.setAnimation(R.raw.order)
                        binding.lottie.playAnimation()
                        binding.orderState = resources.getString(R.string.orderTaken)
                    }
                    1 -> {
                        binding.lottie.setAnimation(R.raw.cook)
                        binding.lottie.playAnimation()
                        binding.orderState = resources.getString(R.string.orderPrep)
                    }

                    2 -> {
                        binding.lottie.setAnimation(R.raw.delivery)
                        binding.lottie.playAnimation()
                        binding.orderState = resources.getString(R.string.orderWay)
                    }
                    3 -> {
                        binding.lottie.setAnimation(R.raw.complate)
                        binding.lottie.playAnimation()
                        binding.okButton.visibility = View.VISIBLE
                        binding.orderState = resources.getString(R.string.orderDelivered)
                    }
                }
            } else {
                binding.textViewOrderEmpty.visibility = View.VISIBLE
            }

            binding.progress = it.orderState

        }
        return binding.root
    }

    fun deleteOrder() {
        viewModel.deleteOrder()
        binding.lottie.visibility = View.INVISIBLE
        binding.seekBar.visibility = View.INVISIBLE
        binding.textView14.visibility = View.INVISIBLE
        binding.okButton.visibility = View.INVISIBLE
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = resources.getColor(R.color.white)
        requireActivity().window.getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        val tempViewModel: OrderViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.statusBarColor = resources.getColor(R.color.white)
        requireActivity().window.getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
    }


}