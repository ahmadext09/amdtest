package com.amd.amdtest.activity

import android.os.Bundle
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity


@Keep
open class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onDestroy() {
        super.onDestroy()
    }
}