package com.talip.lembas.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.talip.lembas.R
import com.talip.lembas.databinding.FragmentResetPasswordBinding
import com.talip.lembas.ui.viewmodel.ResetPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {
    private lateinit var binding: FragmentResetPasswordBinding
    private lateinit var viewModel: ResetPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_reset_password, container, false)
        binding.resetPassFragment = this

        return binding.root
    }

    fun sendPasswordLink(email: String, view: View) {
        viewModel.sendPasswordLink(email, requireContext(), view)
    }

    fun back(view: View) {
        Navigation.findNavController(view).popBackStack()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = resources.getColor(R.color.white)

        val tempViewModel: ResetPasswordViewModel by viewModels()
        viewModel = tempViewModel
    }


}