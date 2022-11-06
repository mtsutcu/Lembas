package com.talip.lembas.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.talip.lembas.R
import com.talip.lembas.databinding.FragmentLoginBinding
import com.talip.lembas.databinding.FragmentSignUpBinding
import com.talip.lembas.ui.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.signUpFragment = this

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.buttonSignUp.visibility = View.INVISIBLE
                binding.signUpProgress.visibility = View.VISIBLE
            } else {
                binding.buttonSignUp.visibility = View.VISIBLE
                binding.signUpProgress.visibility = View.INVISIBLE
            }
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: SignUpViewModel by viewModels()
        viewModel = tempViewModel
    }


    fun signUp(email: String, password: String, re_password: String, address: String, view: View) {

        if (re_password != password) {
            val alert = AlertDialog.Builder(requireContext())
            alert.setTitle("Uyarı")
            alert.setMessage("Şifreler aynı değil")
            alert.setPositiveButton("Tamam") { s, d -> }
            alert.create().show()
        } else {
            viewModel.createUserWithEmail(email, password, address, requireContext(), view)
        }

    }


}