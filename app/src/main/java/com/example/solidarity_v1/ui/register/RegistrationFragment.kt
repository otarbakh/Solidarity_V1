package com.example.solidarity_v1.ui.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.solidarity_v1.R
import com.example.solidarity_v1.common.BaseFragment
import com.example.solidarity_v1.databinding.FragmentRegistrationBinding

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate),AdapterView.OnItemSelectedListener {

    var priority = ""
    override fun viewCreated() {
        setupSpinner()
    }

    override fun listeners() {

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

}