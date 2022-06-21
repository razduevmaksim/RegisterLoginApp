package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_register.*

class Register : Fragment() {

    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Навигация
        textViewSignIn.setOnClickListener{ textView:View ->
            textView.findNavController().navigate(R.id.action_register_to_login)
        }

        //валидация данных (EditText)
        //Валидация Name
        var validationName = false

        editTextRegisterName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validationName = if (editTextRegisterName.text.toString().length>=3) {
                    true
                } else{
                    editTextRegisterName.error = "Invalid Name"
                    false
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })


        //Валидация MobileNumber

        var validationNumber = false
        editTextMobileNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validationNumber = if (editTextMobileNumber.isDone){
                    true
                } else{
                    editTextMobileNumber.error = "Invalid Number"
                    false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        //Валидация Email
        var validationEmail = false

        editTextRegisterEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validationEmail = if (android.util.Patterns.EMAIL_ADDRESS.matcher(editTextRegisterEmail.text.toString()).matches()) {
                    true
                } else{
                    editTextRegisterEmail.error = "Invalid Email"
                    false

                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        //Валидация Password
        var validationPassword = false

        editTextRegisterPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val onlyNumbers = editTextRegisterPassword.text.toString().matches("-?\\d+(\\.\\d+)?".toRegex())

                validationPassword = if (editTextRegisterPassword.text.toString().length < 6 || onlyNumbers){
                    editTextRegisterPassword.error = "Invalid Password"
                    false
                } else{
                    true
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        //SharedPreferences
        preferences = this.requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        buttonToLogin.setOnClickListener { buttonView: View ->

            if (validationName  && validationNumber && validationEmail && validationPassword) {
                //Передача данных в хранилище
                val editor = preferences.edit()
                val userName = editTextRegisterName.text.toString()
                editor.putString(PREF_NAME_VALUE, userName)
                val mobileNumber = editTextMobileNumber.unMasked
                editor.putString(PREF_PHONE_VALUE, mobileNumber)
                val userEmail = editTextRegisterEmail.text.toString()
                editor.putString(PREF_MAIL_VALUE, userEmail)
                val userPassword = editTextRegisterPassword.text.toString()
                editor.putString(PREF_PASSWORD_VALUE, userPassword)
                editor.apply()

                buttonView.findNavController().navigate(R.id.action_register_to_login)
            }
        }
    }
}