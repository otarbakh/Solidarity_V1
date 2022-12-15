package com.example.solidarity_v1.ui.register

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.solidarity_v1.R
import com.example.solidarity_v1.common.BaseFragment
import com.example.solidarity_v1.databinding.FragmentRegistrationBinding

class RegistrationFragment :
    BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate),AdapterView.OnItemSelectedListener{

    private var selectedCountryCode = ""

    override fun viewCreated() {
        setupSpinner()
    }

    override fun listeners() {
        changeColours()
        checkVisibility()
        checknext()
    }



    private fun changeColours() {
        binding.btnEnglish.setOnClickListener {
            binding.btnEnglish.setBackgroundResource(R.drawable.button_outline_gray)

            binding.btnGeorgian.setBackgroundResource(R.drawable.button_outline_blue)

            binding.btnUkrainian.setBackgroundResource(R.drawable.button_outline_blue)

        }
        binding.btnGeorgian.setOnClickListener {
            binding.btnGeorgian.setBackgroundResource(R.drawable.button_outline_gray)

            binding.btnEnglish.setBackgroundResource(R.drawable.button_outline_blue)

            binding.btnUkrainian.setBackgroundResource(R.drawable.button_outline_blue)

        }

        binding.btnUkrainian.setOnClickListener {
            binding.btnUkrainian.setBackgroundResource(R.drawable.button_outline_gray)

            binding.btnEnglish.setBackgroundResource(R.drawable.button_outline_blue)

            binding.btnGeorgian.setBackgroundResource(R.drawable.button_outline_blue)

        }
    }

    private fun checkVisibility() {
        binding.etPhoneNumber.doOnTextChanged { text, start, before, count ->
            binding.btnNext.setBackgroundResource(R.drawable.button_outline_blue)

        }
        if (binding.etPhoneNumber.text!!.isNotEmpty()){
            binding.btnNext.isClickable
        }
    }


    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.country_codes,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.indexSpinner.adapter = adapter
        binding.indexSpinner.onItemSelectedListener = this

    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
       val text:String = parent?.getItemAtPosition(position).toString()
        selectedCountryCode = text
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }


    fun checknext(){
        binding.btnNext.setOnClickListener {
            Toast.makeText(requireContext(), "gogogogo", Toast.LENGTH_SHORT).show()
        }
    }

}