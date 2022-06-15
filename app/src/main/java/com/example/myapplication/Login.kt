package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var preferences: SharedPreferences

/**
 * A simple [Fragment] subclass.
 * Use the [Login.newInstance] factory method to
 * create an instance of this fragment.
 */
class Login : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Login.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Login().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Навигация
        val textViewSignUp = view.findViewById<TextView>(R.id.textViewSignUp)
        textViewSignUp.setOnClickListener{ view:View ->
            view.findNavController().navigate(R.id.action_login_to_register)
        }

        val textViewForgetYourPassword = view.findViewById<TextView>(R.id.textViewForgetYourPassword)
        textViewForgetYourPassword.setOnClickListener{ view:View ->
            view.findNavController().navigate(R.id.action_login_to_resetPassword)
        }

        preferences = this.requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        //Установка email из хранилища
        val userMail = preferences.getString(PREF_MAIL_VALUE, "")
        val editTextLoginEmail = view.findViewById<EditText>(R.id.editTextLoginEmail)
        editTextLoginEmail.setText(userMail)

        //Валидация Email
        var flag1 = false
        if(editTextLoginEmail.text.toString() == userMail){
            flag1 = true
        }
        editTextLoginEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (editTextLoginEmail.text.toString() != userMail) {
                    editTextLoginEmail.error = "Invalid Email"
                    flag1 = false
                } else {
                    flag1 = true
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        val editTextLoginPassword = view.findViewById<EditText>(R.id.editTextLoginPassword)
        val userPassword = preferences.getString(PREF_PASSWORD_VALUE, "")

        //Валидация Password
        var flag2 = false
        editTextLoginPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (editTextLoginPassword.text.toString() != userPassword){
                    editTextLoginPassword.error = "Invalid Password"
                    flag2 = false

                } else{
                    flag2 = true

                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        //Скрыть показать пароль
        val buttonPasswordShowHide = view.findViewById<Button>(R.id.buttonPasswordShowHide)

        buttonPasswordShowHide.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> editTextLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                MotionEvent.ACTION_DOWN -> editTextLoginPassword.setInputType(
                    InputType.TYPE_CLASS_TEXT
                )
            }
            true
        })

        //Переход на MainPage
        val buttonToMainPage = view.findViewById<Button>(R.id.buttonToMainPage)

        buttonToMainPage.setOnClickListener{ view:View ->
            if (flag1 && flag2){
                val intent = Intent(activity, MainPage::class.java)
                activity?.startActivity(intent)
            }
        }
    }
}
