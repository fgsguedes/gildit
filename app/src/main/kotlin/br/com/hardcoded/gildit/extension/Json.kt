package br.com.hardcoded.gildit.extension

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlin.reflect.KClass

private object Json {
  private val gson = Gson()

  fun <T> fromJson(jsonElement: JsonElement, targetClass: Class<T>) = gson.fromJson(jsonElement, targetClass)
}

infix fun JsonObject.getAsString(key: String) = this.get(key).asString

infix fun <T : Any> JsonElement.parsedAs(targetClass: KClass<T>) = parsedAs(targetClass.java)
infix fun <T : Any> JsonElement.parsedAs(targetClass: Class<T>) = Json.fromJson(this, targetClass)
