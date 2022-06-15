package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.myapplication.databinding.ActivityMainBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [Register.newInstance] factory method to
 * create an instance of this fragment.
 */
class Register : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var preferences: SharedPreferences

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
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Register.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Register().apply {
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
        val textViewSignIn = view.findViewById<TextView>(R.id.textViewSignIn)
        textViewSignIn.setOnClickListener{ view:View ->
            view.findNavController().navigate(R.id.action_register_to_login)
        }



        //валидация данных (EditText)
        //Валидация Name
        var flag1 = false

        val editTextRegisterName = view.findViewById<EditText>(R.id.editTextRegisterName)
        editTextRegisterName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                flag1 = if (editTextRegisterName.text.toString().length>=3) {
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
        var flag2 = false

        val editTextMobileNumber = view.findViewById<EditText>(R.id.editTextMobileNumber)
        editTextMobileNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                flag2 =
                    if (android.util.Patterns.PHONE.matcher(editTextMobileNumber.text.toString()).matches()
                        && editTextMobileNumber.text.toString().length==12) {
                        true
                    } else{
                        editTextMobileNumber.error = "Invalid Mobile Phone"
                        false
                    }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        //Валидация Email
        var flag3 = false

        val editTextRegisterEmail = view.findViewById<EditText>(R.id.editTextRegisterEmail)
        editTextRegisterEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                flag3 = if (android.util.Patterns.EMAIL_ADDRESS.matcher(editTextRegisterEmail.text.toString()).matches()) {
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
        var flag4 = false

        val editTextRegisterPassword = view.findViewById<EditText>(R.id.editTextRegisterPassword)
        editTextRegisterPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                flag4 = if (editTextRegisterPassword.text.toString().length < 6 ){
                    editTextRegisterPassword.error = "Invalid Password"
                    false
                } else{
                    true
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        //Скрыть\показать пароль
        val buttonPasswordShowHide = view.findViewById<Button>(R.id.buttonPasswordShowHide)

        buttonPasswordShowHide.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> editTextRegisterPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                MotionEvent.ACTION_DOWN -> editTextRegisterPassword.setInputType(
                    InputType.TYPE_CLASS_TEXT
                )
            }
            true
        })

        //SharedPreferences
        preferences = this.requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        val buttonToLogin = view.findViewById<Button>(R.id.buttonToLogin)
        buttonToLogin.setOnClickListener { view: View ->
            if (flag1 && flag2 && flag3 && flag4) {
                //Передача данных в хранилище
                val editor = preferences.edit()
                val userName = editTextRegisterName.text.toString()
                editor.putString(PREF_NAME_VALUE, userName)
                val userPhone = editTextMobileNumber.text.toString()
                editor.putString(PREF_PHONE_VALUE, userPhone)
                val userEmail = editTextRegisterEmail.text.toString()
                editor.putString(PREF_MAIL_VALUE, userEmail)
                val userPassword = editTextRegisterPassword.text.toString()
                editor.putString(PREF_PASSWORD_VALUE, userPassword)
                editor.apply()

                view.findNavController().navigate(R.id.action_register_to_login)
            }
        }
    }
}