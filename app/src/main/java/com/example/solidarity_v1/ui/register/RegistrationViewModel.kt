package com.example.solidarity_v1.ui.register

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class RegistrationViewModel : ViewModel() {

    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var mauth: FirebaseAuth


    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d("GFG", "onVerificationCompleted Success")
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.d("GFG", "onVerificationFailed  $e")
        }
        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {

            storedVerificationId = verificationId
            resendToken = token
            Log.d("GFG", "onCodeSent: $verificationId")
            Log.d("GFG", "onCodeSent: $token")
        }
    }

    fun sendVerificationCode(context: Activity,num:String) {
        mauth = Firebase.auth
        val options = PhoneAuthOptions.newBuilder(mauth)
            .setPhoneNumber(num) // Phone number to verify
            .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(context) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        Log.d("GFG" , "Auth started")
    }
}