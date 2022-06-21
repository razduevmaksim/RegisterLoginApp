package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_login.*

private lateinit var preferences: SharedPreferences

class Login : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Навигация
        textViewSignUp.setOnClickListener{ textView:View ->
            textView.findNavController().navigate(R.id.action_login_to_register)
        }

        textViewForgetYourPassword.setOnClickListener{ textView:View ->
            textView.findNavController().navigate(R.id.action_login_to_resetPassword)
        }

        preferences = this.requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        //Установка email из хранилища
        val userMail = preferences.getString(PREF_MAIL_VALUE, "")
        editTextLoginEmail.setText(userMail)
        var validationEmail = true

        //Валидация Email

        editTextLoginEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validationEmail = if (android.util.Patterns.EMAIL_ADDRESS.matcher(editTextLoginEmail.text.toString()).matches()) {
                    true
                } else{
                    editTextLoginEmail.error = "Invalid Email"
                    false

                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        //Валидация Password
        var validationPassword = false

        editTextLoginPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val onlyNumbers = editTextLoginPassword.text.toString().matches("-?\\d+(\\.\\d+)?".toRegex())

                validationPassword = if (editTextLoginPassword.text.toString().length < 6 || onlyNumbers){
                    editTextLoginPassword.error = "Invalid Password"
                    false
                } else{
                    true
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })


        //Переход на MainPage
        buttonToMainPage.setOnClickListener{
            if (validationEmail && validationPassword){
                val intent = Intent(activity, MainPage::class.java)
                activity?.startActivity(intent)
            }
        }
    }
}
