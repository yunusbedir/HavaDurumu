package com.yunusbedir.havadurumu.Data.Csv

import android.content.Context
import android.util.Log
import com.yunusbedir.havadurumu.Model.Region
import com.yunusbedir.havadurumu.R
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by YUNUS BEDİR on 13.05.2020.
 */
class ReadCsv(private val app: Context) {

    fun getSearchedList(text: String): List<Region> {
        val resultList = ArrayList<Region>()
        val inputStream: InputStream = app.resources.openRawResource(R.raw.tr_koordinat)
        val reader = BufferedReader(InputStreamReader(inputStream))

        var line: String? = reader.readLine()

        while (line != null) {
            val splitLine = line.split(";")
            if (splitLine[0].toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                val region =
                    Region(splitLine[0], splitLine[1].toInt(), splitLine[2].toInt())
                Log.i("ReadCsv", region.toString())
                resultList.add(region)
            }
            line = reader.readLine()
        }
        return resultList
    }
}