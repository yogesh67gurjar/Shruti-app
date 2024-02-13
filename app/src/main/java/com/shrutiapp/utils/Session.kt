package com.shrutiapp.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Session @Inject constructor(@ApplicationContext context:Context) {


    val prefs: SharedPreferences =
        context.getSharedPreferences(context.packageName, MODE_PRIVATE)

    fun getData(key: String): String {
        return prefs.getString(key, "")!!
    }

    fun setData(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun setBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return prefs.getBoolean(key, false)
    }

    fun clearData() {
        prefs.edit().clear().apply()
    }

    fun getObjectData(keyName: String, classOfT: Class<*>?): Any? {

        val json = prefs?.getString(keyName, "{}")
        val gson = Gson()
        val jsonObj = gson.fromJson(json, classOfT)
        return jsonObj

    }

    fun putObjectData(keyName: String, modelObj: Object) {

        val gson = Gson()
        val jsonObject = gson.toJson(modelObj)
        var editor = prefs.edit()
        editor.putString(keyName, jsonObject)
        editor.commit()
    }

}