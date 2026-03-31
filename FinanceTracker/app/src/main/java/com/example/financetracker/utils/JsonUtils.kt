package com.example.financetracker.utils

import android.content.Context
import android.util.Log
import com.example.financetracker.domain.model.Category
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object JsonUtils {

    fun loadJsonFromAssets(context: Context, fileName: String): String? {
        return try {
            val assetManager = context.assets
            val files = assetManager.list("")
            if (files != null) {
                for (file in files) {
                    Log.d("Assets", "Asset file: $file")
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
            category.copy(
                uid = uid,
                isCustom = false
            )
        }
    }

}