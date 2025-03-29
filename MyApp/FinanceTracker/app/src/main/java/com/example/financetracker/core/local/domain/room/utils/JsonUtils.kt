package com.example.financetracker.core.local.domain.room.utils

import android.content.Context
import android.util.Log
import com.example.financetracker.core.local.domain.room.model.Category
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object JsonUtils {

    fun loadJsonFromAssets(context: Context, fileName: String): String? {
        return try {
            val assetManager = context.assets
            val files = assetManager.list("") // List all assets
            if (files != null) {
                for (file in files) {
                    Log.d("Assets", "Asset file: $file") // Debugging log
                }
            }

            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }


    fun parseJsonToCategories(jsonString: String): List<Category> {
        val listType = object : TypeToken<List<Category>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }

}