package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_reset_password.*
import kotlinx.android.synthetic.main.fragment_reset_password.buttonToLogin
import kotlinx.android.synthetic.main.fragment_reset_password.textViewSignUp


private lateinit var preferences: SharedPreferences

class ResetPassword : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_password, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = this.requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        textViewSignUp.setOnClickListener{ textView:View ->
            textView.findNavController().navigate(R.id.action_resetPassword_to_register)
        }

        //Валидация Email
        var validationEmail = false

        editTextResetPasswordEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validationEmail = if (android.util.Patterns.EMAIL_ADDRESS.matcher(editTextResetPasswordEmail.text.toString()).matches()) {
                    true
                } else{
                    editTextResetPasswordEmail.error = "Invalid Email"
                    false

                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        //Валидация Password
        var validationPassword = false

        editTextResetPasswordPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val onlyNumbers = editTextResetPasswordPassword.text.toString().matches("-?\\d+(\\.\\d+)?".toRegex())

                validationPassword = if (editTextResetPasswordPassword.text.toString().length < 6 || onlyNumbers){
                    editTextResetPasswordPassword.error = "Invalid Password"
                    false
                } else{
                    true
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        //Валидация ConfirmPassword
        var validationConfirmPassword = false

        editTextResetPasswordConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validationConfirmPassword = if (editTextResetPasswordConfirmPassword.text.toString()!= editTextResetPasswordPassword.text.toString() ){
                    editTextResetPasswordConfirmPassword.error = "Invalid Password"
                    false
                } else{
                    true
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        buttonToLogin.setOnClickListener{buttonView:View ->
            if (validationEmail && validationPassword && validationConfirmPassword) {
                //Изменение данных в хранилище
                val editor = preferences.edit()

                val userPassword = editTextResetPasswordConfirmPassword.text.toString()
                editor.putString(PREF_PASSWORD_VALUE, userPassword)
                editor.apply()

                // Toast
                Toast.makeText(activity,"Новый пароль отправлен на указанный email" ,Toast.LENGTH_SHORT).show()

                buttonView.findNavController().navigate(R.id.action_resetPassword_to_login)
            }
        }
    }
}