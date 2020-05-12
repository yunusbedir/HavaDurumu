package com.yunusbedir.havadurumu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yunusbedir.havadurumu.Repository.Csv.ReadCsv

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ReadCsv(this).getSearchedList("Sul")
    }
}
