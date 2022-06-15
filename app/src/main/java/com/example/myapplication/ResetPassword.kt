package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.*
import android.view.View.OnTouchListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController


private lateinit var preferences: SharedPreferences

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResetPassword.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResetPassword : Fragment() {
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
        return inflater.inflate(R.layout.fragment_reset_password, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResetPassword.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResetPassword().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = this.requireActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        val textViewSignUp = view.findViewById<TextView>(R.id.textViewSignUp)
        textViewSignUp.setOnClickListener{ view:View ->
            view.findNavController().navigate(R.id.action_resetPassword_to_register)
        }

        //Валидация Email
        var flag1 = false
        val userMail = preferences.getString(PREF_MAIL_VALUE, "")

        val editTextResetPasswordEmail = view.findViewById<EditText>(R.id.editTextResetPasswordEmail)
        editTextResetPasswordEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                flag1 = if (editTextResetPasswordEmail.text.toString() != userMail ){
                    editTextResetPasswordEmail.error = "Invalid Email"
                    false
                } else{
                    true
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        //Валидация Password
        var flag2 = false

        val editTextResetPasswordPassword = view.findViewById<EditText>(R.id.editTextResetPasswordPassword)
        editTextResetPasswordPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                flag2 = if (editTextResetPasswordPassword.text.toString().length < 6 ){
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
        var flag3 = false

        val editTextResetPasswordConfirmPassword = view.findViewById<EditText>(R.id.editTextResetPasswordConfirmPassword)
        editTextResetPasswordConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                flag3 = if (editTextResetPasswordConfirmPassword.text.toString()!= editTextResetPasswordPassword.text.toString() ){
                    editTextResetPasswordConfirmPassword.error = "Invalid Password"
                    false
                } else{
                    true
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        //Скрыть\показать пароль

        val buttonPasswordShowHideConfirmPassword = view.findViewById<Button>(R.id.buttonPasswordShowHideConfirmPassword)
        val buttonPasswordShowHide = view.findViewById<Button>(R.id.buttonPasswordShowHide)

        buttonPasswordShowHideConfirmPassword.setOnTouchListener(OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> editTextResetPasswordConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                MotionEvent.ACTION_DOWN -> editTextResetPasswordConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT)
            }
            true
        })
        buttonPasswordShowHide.setOnTouchListener(OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> editTextResetPasswordPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                MotionEvent.ACTION_DOWN -> editTextResetPasswordPassword.setInputType(InputType.TYPE_CLASS_TEXT)
            }
            true
        })

        val buttonToLogin = view.findViewById<Button>(R.id.buttonToLogin)

        buttonToLogin.setOnClickListener{view:View ->
            if (flag1 && flag2 && flag3) {
                //Изменение данных в хранилище
                val editor = preferences.edit()

                val userPassword = editTextResetPasswordConfirmPassword.text.toString()
                editor.putString(PREF_PASSWORD_VALUE, userPassword)
                editor.apply()

                // Toast
                Toast.makeText(getActivity(),"Новый пароль отправлен на указанный email" ,Toast.LENGTH_SHORT).show();

                view.findNavController().navigate(R.id.action_resetPassword_to_login)
            }
        }
    }
}