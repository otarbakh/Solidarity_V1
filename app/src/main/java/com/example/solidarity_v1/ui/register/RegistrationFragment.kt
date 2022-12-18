package com.example.solidarity_v1.ui.register

import android.R
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.solidarity_v1.common.BaseFragment
import com.example.solidarity_v1.databinding.FragmentRegistrationBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit


class RegistrationFragment :
    BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate),
    AdapterView.OnItemSelectedListener {



    var mVerificationId: String? = null
    var mResendToken: ForceResendingToken? = null

    private lateinit var auth: FirebaseAuth
    private var selectedCountryCode = ""

    override fun viewCreated() {

        auth = Firebase.auth
        setupSpinner()
    }

    override fun listeners() {
        checkCode()

        sendOtp("+995551585021")
        changeColours()
        checkVisibility()
        checknext()
    }


    private fun changeColours() {
        binding.btnEnglish.setOnClickListener {
            binding.btnEnglish.setBackgroundResource(com.example.solidarity_v1.R.drawable.button_outline_gray)

            binding.btnGeorgian.setBackgroundResource(com.example.solidarity_v1.R.drawable.button_outline_blue)

            binding.btnUkrainian.setBackgroundResource(com.example.solidarity_v1.R.drawable.button_outline_blue)

        }
        binding.btnGeorgian.setOnClickListener {
            binding.btnGeorgian.setBackgroundResource(com.example.solidarity_v1.R.drawable.button_outline_gray)

            binding.btnEnglish.setBackgroundResource(com.example.solidarity_v1.R.drawable.button_outline_blue)

            binding.btnUkrainian.setBackgroundResource(com.example.solidarity_v1.R.drawable.button_outline_blue)

        }

        binding.btnUkrainian.setOnClickListener {
            binding.btnUkrainian.setBackgroundResource(com.example.solidarity_v1.R.drawable.button_outline_gray)

            binding.btnEnglish.setBackgroundResource(com.example.solidarity_v1.R.drawable.button_outline_blue)

            binding.btnGeorgian.setBackgroundResource(com.example.solidarity_v1.R.drawable.button_outline_blue)

        }
    }

    private fun checkVisibility() {
        binding.etPhoneNumber.doOnTextChanged { text, start, before, count ->
            binding.btnNext.setBackgroundResource(com.example.solidarity_v1.R.drawable.button_outline_blue)

        }
        if (binding.etPhoneNumber.text!!.isNotEmpty()) {
            binding.btnNext.isClickable
        }
    }


    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            com.example.solidarity_v1.R.array.country_codes,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.indexSpinner.adapter = adapter
        binding.indexSpinner.onItemSelectedListener = this

    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text: String = parent?.getItemAtPosition(position).toString()
        selectedCountryCode = text
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    fun checknext() {
        binding.btnNext.setOnClickListener {
            Toast.makeText(requireContext(), "gogogogo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendOtp(mobile_no: String) {
        val options: PhoneAuthOptions = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(mobile_no) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }




    private val mCallbacks: OnVerificationStateChangedCallbacks =

        object : OnVerificationStateChangedCallbacks()
        {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                //Getting the code sent by SMS
                val code = credential.smsCode
                Log.d("yeep", code.toString())
                //sometime the code is not detected automatically
                //in this case the code will be null
                //so user has to manually enter the code

                if (code != null){
                    verifyVerificationCode(code)
                    Log.d("YEEP" , "145")

                }
//                code?.let { verifyVerificationCode(it) }
            }
//            saaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa

            override fun onVerificationFailed(e: FirebaseException) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                if (e is FirebaseAuthInvalidCredentialsException) {
                    Log.d("YEEP" , "155")
                    // Invalid request
                    // ...
                } else if (e is FirebaseTooManyRequestsException) {

                    Log.d("YEEP" , e.message.toString())
                    // The SMS quota for the project has been exceeded
                    // ...
                }
                // Show a message and update the UI
                // ...
            }

            override fun onCodeSent(verificationId: String, token: ForceResendingToken) {
                super.onCodeSent(verificationId, token)
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId
                Log.d("YEEP" , mVerificationId.toString())
                mResendToken = token
                Log.d("YEEP" , mResendToken.toString())
            }
        }



    private fun verifyVerificationCode(code: String) {
        try {
            Log.d("YEEP" , "180")
            //creating the credential
            val credential = PhoneAuthProvider.getCredential(mVerificationId!!, code)
            //signing the user
            signInWithPhoneAuthCredential(credential)
        } catch (e: Exception) {
            Log.d("YEEP" , e.message.toString())


        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity(),
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {

                        Log.d("YEEP" , "193")



                        // Sign in success, update UI with the signed-in user's information
//                        val user: FirebaseUser = task.result.user


                    } else {
                        Log.d("YEEP" , "202")
//                        var message = getString(R.string.error_message)
//                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                            message = getString(R.string.invalid_code)
//                        }
//                        val snackbar = Snackbar.make(
//                            findViewById(R.id.content),
//                            message, Snackbar.LENGTH_LONG
//                        )
//                        snackbar.setAction(R.string.dismiss, View.OnClickListener { })
//                        snackbar.show()
                    }
                })
    }


    fun checkCode(){
        binding.btnNext.setOnClickListener {
            verifyVerificationCode(binding.etPhoneNumber.text.toString())
            Log.d("YEEP" , "221")
        }
    }



}