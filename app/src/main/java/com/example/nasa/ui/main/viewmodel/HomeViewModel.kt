package com.example.nasa.ui.main.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.nasa.data.model.NasaImageResponseItem
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    var nasaImageDta: ArrayList<NasaImageResponseItem>? = null
    var currentPosition = 0

    /**
     * this method reads data from data.json file from assets
     */
    fun getJsonDataFromAsset(context: Context): Boolean {
        val jsonString: String
        try {
            jsonString = context.assets.open("data.json").bufferedReader().use { it.readText() }

            val gson = GsonBuilder().create()
            val theList = gson.fromJson<ArrayList<NasaImageResponseItem>>(
                jsonString,
                object :
                    TypeToken<ArrayList<NasaImageResponseItem>>() {}.type
            )
            nasaImageDta = theList
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return false
        }
        return true
    }
}
