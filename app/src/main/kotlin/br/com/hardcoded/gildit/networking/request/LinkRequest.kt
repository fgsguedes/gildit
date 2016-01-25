package br.com.hardcoded.gildit.networking.request

import android.util.Log
import br.com.hardcoded.gildit.extension.asInstanceOf
import br.com.hardcoded.gildit.model.Kind
import br.com.hardcoded.gildit.model.Thing
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.ServerError
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.JsonParser

class LinkRequest(requestUrl: String, listener: Response.Listener<Array<Thing.Link>>, errorListener: Response.ErrorListener) :
    BaseRequest<Array<Thing.Link>>(Request.Method.GET, requestUrl, listener, errorListener) {

  companion object {
    val TAG = LinkRequest::class.simpleName
  }

  override fun parseNetworkResponse(networkResponse: NetworkResponse): Response<Array<Thing.Link>> {
    val serverResponse = String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers, "UTF-8"))
    Log.d(TAG, "Request response -> url=`$requestUrl` statusCode=`${networkResponse.statusCode}` response=`$serverResponse")

    val jsonResponse = JsonParser().parse(serverResponse).asJsonObject

    val children = when (jsonResponse.get("kind").asString) {
      "Listing" -> jsonResponse.getAsJsonObject("data").getAsJsonArray("children")
      else -> null
    } ?: return Response.error(ServerError())

    val links = children
        .map { it.asJsonObject }
        .filter { it.get("kind").asString == Kind.LINK.prefix }
        .map { it.getAsJsonObject("data") asInstanceOf Thing.Link::class.java }

    return Response.success(links.toTypedArray(), HttpHeaderParser.parseCacheHeaders(networkResponse))
  }
}
