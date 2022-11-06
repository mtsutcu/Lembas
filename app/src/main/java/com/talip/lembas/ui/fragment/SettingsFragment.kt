package com.talip.lembas.ui.fragment

import android.app.AlertDialog
import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.talip.lembas.MainActivity
import com.talip.lembas.R
import com.talip.lembas.databinding.FragmentSettingsBinding
import com.talip.lembas.ui.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var viewModel: SettingsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        binding.settingsFragment = this
        viewModel.address.observe(viewLifecycleOwner) {
            Log.e("addr", " Address Fragment: ${it}")

            binding.address = it
        }

        return binding.root
    }

    fun updateAddressEnable() {
        binding.editTextSetAddress.isEnabled = !binding.editTextSetAddress.isEnabled

        if (binding.editTextSetAddress.isEnabled) {
            binding.changeAddressBut.visibility = View.INVISIBLE
            binding.updateAddressBut.visibility = View.VISIBLE

        } else {
            binding.changeAddressBut.visibility = View.VISIBLE
            binding.updateAddressBut.visibility = View.INVISIBLE

        }
    }

    fun updateAdress(address: String) {
        viewModel.updateAddress(address)
        Log.e("addr", " Address Fragment: ${address}")
        updateAddressEnable()
    }

    fun exit() {
        var alert = AlertDialog.Builder(requireContext())

        alert.setMessage(R.string.wantExit)
        alert.setPositiveButton(R.string.yes) { s, d -> (requireActivity() as MainActivity).exit() }
        alert.setNegativeButton(R.string.no) { s, d -> }
        alert.show()

    }

    fun changePassword(view: View) {
        var alert = AlertDialog.Builder(requireContext())
        alert.setMessage("${resources.getString(R.string.resetLink2)}: ${viewModel.user.value!!.email}")
        alert.setPositiveButton(R.string.yes) { s, d ->
            viewModel.sendPasswordLink(
                requireContext(),
                view
            )
        }
        alert.setNegativeButton(R.string.no) { s, d -> }
        alert.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.statusBarColor = resources.getColor(R.color.white)
        requireActivity().window.getDecorView()
            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        val tempViewModel: SettingsViewModel by viewModels()
        viewModel = tempViewModel
    }


}