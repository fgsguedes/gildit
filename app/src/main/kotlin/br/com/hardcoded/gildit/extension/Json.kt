package br.com.hardcoded.gildit.extension

import com.google.gson.Gson
import com.google.gson.JsonElement

object Json {
  private val gson = Gson()

  fun <T> fromJson(jsonElement: JsonElement, targetClass: Class<T>) = gson.fromJson(jsonElement, targetClass)
}

infix fun <T> JsonElement.asInstanceOf(targetClass: Class<T>) = Json.fromJson(this, targetClass)