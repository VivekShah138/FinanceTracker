package com.example.financetracker.core.local.domain.room.utils

import android.content.Context
import android.util.Log
import com.example.financetracker.core.local.domain.room.model.Category
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.util.UUID

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


    fun parseJsonToCategories(jsonString: String, uid: String): List<Category> {
        val listType = object : TypeToken<List<Category>>() {}.type
        val categories: List<Category> = Gson().fromJson(jsonString, listType)
        Log.d("Categories", "Categories $categories")

        return categories.map {category->
            // For each category, generate a unique uid and set isCustom to false
            category.copy(
                uid = uid,
                isCustom = false  // Assuming these categories are predefined
            )
        }
    }

}