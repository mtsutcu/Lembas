package com.talip.lembas.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.talip.lembas.MainActivity
import com.talip.lembas.R
import com.talip.lembas.databinding.FragmentLoginBinding
import com.talip.lembas.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.loginFragment = this

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.buttonLogin.visibility = View.INVISIBLE
                binding.loginProgress.visibility = View.VISIBLE
            } else {
                binding.buttonLogin.visibility = View.VISIBLE
                binding.loginProgress.visibility = View.INVISIBLE
            }
        }

        viewModel.user.observe(viewLifecycleOwner) {

            Log.e("viewc", "LOGIN OBSERVE USER : $it")
            if (it.user_id != null) {
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
                requireActivity().finish()
            }
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = resources.getColor(R.color.myOrange)
        requireActivity().window.getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        val tempViewModel: LoginViewModel by viewModels()
        viewModel = tempViewModel

    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.statusBarColor = resources.getColor(R.color.white)

    }

    fun login(email: String, password: String) {
        viewModel.login(email, password, requireContext())
        Log.e("viewc", "Login button çalıştı")

    }

    fun toSignUp(view: View) {
        val trans = LoginFragmentDirections.toSignUp()
        Navigation.findNavController(view).navigate(trans)
    }

    fun toReset(view: View) {
        val trans = LoginFragmentDirections.toReset()
        Navigation.findNavController(view).navigate(trans)
    }

}