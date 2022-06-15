package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.EditText
import com.example.myapplication.databinding.ActivityMainBinding

const val APP_PREFERENCES = "APP_PREFERENCES "
var PREF_NAME_VALUE = "PREF_NAME_VALUE"
var PREF_PHONE_VALUE = "PREF_PHONE_VALUE"
var PREF_MAIL_VALUE = "PREF_MAIL_VALUE"
var PREF_PASSWORD_VALUE = "PREF_PASSWORD_VALUE"

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()?.hide();
    }

}