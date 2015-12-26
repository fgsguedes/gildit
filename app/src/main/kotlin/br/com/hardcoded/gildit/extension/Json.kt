package br.com.hardcoded.gildit.extension

import com.google.gson.Gson


private val gson = Gson()
fun <T> Class<T>.fromJson(jsonString: String) = gson.fromJson(jsonString, this)