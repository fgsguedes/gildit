package br.com.hardcoded.gildit.networking.request

import android.util.Log
import br.com.hardcoded.gildit.BuildConfig
import br.com.hardcoded.gildit.extension.fromJson
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser

abstract class BaseRequest<T>(val targetClass: Class<T>, method: Int, val requestUrl: String, val listener: Response.Listener<T>, errorListener: Response.ErrorListener)
: Request<T>(method, requestUrl, errorListener) {

  companion object {
    private val TAG = BaseRequest::class.simpleName
  }

  override fun getPriority() = Priority.HIGH

  override fun getHeaders() = mapOf(
      "User-agent" to "android:${BuildConfig.APPLICATION_ID}:${BuildConfig.VERSION_NAME} (by /u/fgsguedes)"
  )

  override fun parseNetworkResponse(networkResponse: NetworkResponse): Response<T> {
    val serverResponse = String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers, "UTF-8"))
    Log.d(TAG, "Request response -> url=`$requestUrl` statusCode=`${networkResponse.statusCode}` response=`$serverResponse")

    return Response.success(targetClass.fromJson(serverResponse), HttpHeaderParser.parseCacheHeaders(networkResponse))
  }

  override fun deliverResponse(t: T) = listener.onResponse(t)
}