package br.com.hardcoded.gildit.networking

import br.com.hardcoded.gildit.extension.asInstanceOf
import br.com.hardcoded.gildit.model.Kind
import br.com.hardcoded.gildit.model.Thing
import com.google.gson.JsonParser
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.reflect.GenericArrayType
import java.lang.reflect.Type

interface SubredditRequest {

  @GET("/hot.json?raw_json=1")
  fun frontpage(): Call<Array<Thing.Link>>

  @GET("/r/{subreddit}/hot.json?raw_json=1")
  fun hotOf(@Path("subreddit") subreddit: String): Call<Array<Thing.Link>>
}


class LinkConverter() : Converter.Factory() {

  override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>?, retrofit: Retrofit): Converter<ResponseBody, Array<Thing.Link>>? {
    return if (type is GenericArrayType && type.genericComponentType == Thing.Link::class.java) {
      Converter {
        val jsonResponse = JsonParser().parse(it.string()).asJsonObject

        val children = when (jsonResponse.get("kind").asString) {
          "Listing" -> jsonResponse.getAsJsonObject("data").getAsJsonArray("children")
          else -> null
        } ?: return@Converter null

        children
            .map { it.asJsonObject }
            .filter { it.get("kind").asString == Kind.LINK.prefix }
            .map { it.getAsJsonObject("data") asInstanceOf Thing.Link::class.java }
            .toTypedArray()
      }
    } else null
  }

  override fun requestBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit): Converter<*, RequestBody>? {
    return null
  }

  override fun stringConverter(type: Type, annotations: Array<out Annotation>?): Converter<*, String>? {
    return null
  }
}