package com.example.solidarity_v1.ui.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.solidarity_v1.R
import com.example.solidarity_v1.common.BaseFragment
import com.example.solidarity_v1.databinding.FragmentRegistrationBinding
import kotlinx.coroutines.launch

class RegistrationFragment :
    BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate),
    AdapterView.OnItemSelectedListener {

    private val vm: RegistrationViewModel by viewModels()

    var priority = ""
    override fun viewCreated() {
        setupSpinner()
    }

    override fun listeners() {
        checkVisibility()
        makeSelectionGray()
        getCode()
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.country_codes,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.countryCodes.adapter = adapter
        binding.countryCodes.onItemSelectedListener = this

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text: String = parent?.getItemAtPosition(position).toString()
        priority = text

    }

    private fun checkVisibility() {
        binding.etPhoneNumber.doOnTextChanged { text, start, before, count ->
            binding.btnNext.setBackgroundResource(R.drawable.button_outline_blue)
        }
    }

    private fun makeSelectionGray() {
        binding.btnEnglish.setOnClickListener {
            binding.btnEnglish.setBackgroundResource(R.drawable.button_outline_gray)
            binding.btnGeorgian.setBackgroundResource(R.drawable.button_outline_blue)
            binding.btnUkrainian.setBackgroundResource(R.drawable.button_outline_blue)
        }

        binding.btnGeorgian.setOnClickListener {
            binding.btnEnglish.setBackgroundResource(R.drawable.button_outline_blue)
            binding.btnGeorgian.setBackgroundResource(R.drawable.button_outline_gray)
            binding.btnUkrainian.setBackgroundResource(R.drawable.button_outline_blue)
        }

        binding.btnUkrainian.setOnClickListener {
            binding.btnEnglish.setBackgroundResource(R.drawable.button_outline_blue)
            binding.btnGeorgian.setBackgroundResource(R.drawable.button_outline_blue)
            binding.btnUkrainian.setBackgroundResource(R.drawable.button_outline_gray)
        }
    }

    private fun getCode() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding.btnNext.setOnClickListener {
                    val numb = binding.etPhoneNumber.text.toString()
                    vm.sendVerificationCode(requireActivity(),numb)
                }
            }
        }
    }
}