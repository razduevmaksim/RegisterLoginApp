package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

const val APP_PREFERENCES = "APP_PREFERENCES "
var PREF_NAME_VALUE = "PREF_NAME_VALUE"
var PREF_PHONE_VALUE = "PREF_PHONE_VALUE"
var PREF_MAIL_VALUE = "PREF_MAIL_VALUE"
var PREF_PASSWORD_VALUE = "PREF_PASSWORD_VALUE"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }
}