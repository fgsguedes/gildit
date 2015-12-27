package br.com.hardcoded.gildit.networking.request

import br.com.hardcoded.gildit.BuildConfig
import com.android.volley.Request
import com.android.volley.Response

abstract class BaseRequest<T>(method: Int, val requestUrl: String, val listener: Response.Listener<T>, errorListener: Response.ErrorListener)
: Request<T>(method, requestUrl, errorListener) {

  override fun getHeaders() = mapOf(
      "User-agent" to "android:${BuildConfig.APPLICATION_ID}:${BuildConfig.VERSION_NAME} (by /u/fgsguedes)"
  )

  override fun deliverResponse(t: T) = listener.onResponse(t)
}